package com.arya.risetinformatika.ui.diagnosis

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arya.risetinformatika.R
import com.arya.risetinformatika.data.Result
import com.arya.risetinformatika.data.local.entity.Patient
import com.arya.risetinformatika.databinding.FragmentDiagnosisBinding
import com.arya.risetinformatika.helper.ImageUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class DiagnosisFragment : Fragment() {
    private var _binding: FragmentDiagnosisBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiagnosisViewModel by viewModels()

    private var currentFile: File? = null

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val newFile = ImageUtils.uriToFile(selectedImg, requireContext())
            currentFile = newFile
            binding.ivUploadedImage.setImageURI(selectedImg)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiagnosisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAction()
        observePredictResult()
    }

    private fun setUpAction() {
        binding.imgBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imgBtnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnPredict.setOnClickListener {
            startPredict()
        }
    }

    private fun observePredictResult() {
        viewModel.predictResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.btnPredict.setLoading(true)
                    }
                    is Result.Success-> {
                        binding.btnPredict.setLoading(false)
                        viewModel.insertPatient(
                            Patient(
                                patientName = result.data.data.patientName,
                                diagnosticResult = result.data.data.diagnosticResult
                            )
                        )
                        findNavController().navigate(
                            R.id.action_diagnosisFragment_to_homeFragment
                        )
                    }
                    is Result.Error -> {
                        binding.btnPredict.setLoading(false)
                        Snackbar.make(
                            binding.root,
                            result.error,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startPredict() {
        if (currentFile != null) {
            val requestImageFile = (currentFile as File)
                .asRequestBody("image/jpeg".toMediaTypeOrNull())
            val file: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                (currentFile as File).name,
                requestImageFile
            )
            val patientName = binding.edtPatientName.text.toString()
                .toRequestBody("text/plain".toMediaType())
            viewModel.predict(file, patientName)
        } else {
            Snackbar.make(
                binding.root,
                "Chest x-ray photo can\'t be empty!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}