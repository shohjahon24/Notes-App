package com.shohjahon.notesapp.presentation.ui.note

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.shohjahon.notesapp.R
import com.shohjahon.notesapp.databinding.FragmentNoteBinding
import com.shohjahon.notesapp.domain.model.note.Image
import com.shohjahon.notesapp.domain.model.note.Note
import com.shohjahon.notesapp.presentation.adapters.ImageAdapter
import com.shohjahon.notesapp.util.deleteFolder
import com.shohjahon.notesapp.util.readBitmapFromStorage
import com.shohjahon.notesapp.util.writeBitmapToStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private var time: Long? = null

    @Inject
    lateinit var imageAdapter: ImageAdapter

    val noteViewModel: NoteViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        time = arguments?.getLong("time")
        binding.listImage.adapter = imageAdapter
        binding.listImage.layoutManager = GridLayoutManager(context, 3)
        time?.let { noteViewModel.getByTime(it) }
        getNote()
        binding.title.setOnFocusChangeListener { _, hasFocus ->
            binding.title.isCursorVisible = hasFocus
        }
        binding.text.setOnFocusChangeListener { _, hasFocus ->
            binding.text.isCursorVisible = hasFocus
        }
        binding.back.setOnClickListener {
           finish()
        }
        binding.image.setOnClickListener {
            selectPhoto()
        }
        binding.save.setOnClickListener { save() }
      }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectPhoto() {
        context?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU || ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
            ) {
               // showSettingsDialog()
            } else {
                var intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                intent = Intent.createChooser(intent, "data")

                val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                intent.putExtra(
                    Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePhotoIntent)
                )
                resultLauncher.launch(intent)
            }
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // There are no request codes
            val data: Intent? = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                val photo = if (data.data == null)
                    data.extras?.get("data") as Bitmap
                else {
                    val stream = requireContext().contentResolver.openInputStream(data.data!!)
                    BitmapFactory.decodeStream(stream)
                }
                imageAdapter.addData(Image(UUID.randomUUID().toString(), photo))
            }
        }

    private fun save() {
        if (binding.title.text.isEmpty())
            Toast.makeText(context, "Please fill the title", Toast.LENGTH_SHORT).show()
        else {
            time?.let {
                noteViewModel.delete(it)
            }
            deleteFolder("${requireContext().filesDir.absolutePath}/$time")
            val time = System.currentTimeMillis()
            val images = imageAdapter.getData()
            images.forEachIndexed { index, image ->
                image.bitmap?.let {
                    writeBitmapToStorage(
                        requireContext().filesDir.absolutePath, "${image.name}.png",
                        it
                    )
                }
            }
            noteViewModel.create(
                Note(title = binding.title.text.toString(), time = time, images = images, text = binding.text.text.toString())
            )
            finish()
        }
    }

    private fun getNote() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            noteViewModel.note.collectLatest { it ->
                it?.let { it1 ->
                    binding.title.setText(it1.title)
                    binding.text.setText(it1.text)
                    it.images.map { it.bitmap = readBitmapFromStorage(requireContext().filesDir.absolutePath, "${it.name}.png") }
                    imageAdapter.setData(it.images)
                }
            }
        }
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}