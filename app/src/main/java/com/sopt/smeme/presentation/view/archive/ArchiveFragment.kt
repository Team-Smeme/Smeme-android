package com.sopt.smeme.presentation.view.archive

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.R
import com.sopt.smeme.bridge.controller.response.ArchiveData
import com.sopt.smeme.business.adaptor.ArchiveAdapter
import com.sopt.smeme.business.adaptor.MyDiaryAdaptor
import com.sopt.smeme.business.viewmodel.ArchiveReader
import com.sopt.smeme.business.viewmodel.ArchiveRemover
import com.sopt.smeme.databinding.FragmentArchiveBinding
import com.sopt.smeme.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class ArchiveFragment @Inject constructor(
    @ApplicationContext context: Context,
) : Fragment() {
    private val archiveReader: ArchiveReader by viewModels()
    private val archiveRemover: ArchiveRemover by viewModels()

    private var _binding: FragmentArchiveBinding? = null
    private val binding: FragmentArchiveBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constructLayout()
        listen()
    }

    private fun constructLayout() {
        val adapter = ArchiveAdapter(
            removeItem = {
                archiveRemover.removeItem(
                    it,
                    onError = {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    })
                archiveReader.getList()
                // TODO : not network, just mutable list
                /*val archiveData = archiveReader.archives.value as ArchiveData
                val first = archiveData.scraps
                    .filter { archiveData -> archiveData.id == it }*/
            },
            context = requireContext()
        )
        binding.rvExpressionArchive.adapter = adapter
        observe(adapter = adapter)

        archiveReader.getList(
            onError = {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        )


    }

    private fun checkMyDiaryExist(adaptor: ArchiveAdapter) {
        if (adaptor.itemCount > 0) {
            binding.rvExpressionArchive.visibility = View.VISIBLE
            binding.tvEmptyArchive.visibility = View.GONE
        }
        else{
            binding.rvExpressionArchive.visibility = View.GONE
            binding.tvEmptyArchive.visibility = View.VISIBLE
        }
    }

    private fun listen() {
        binding.tvDiaryTitleArchive.setOnClickListener {
            Snackbar.make(binding.root, "??? ?????? ???????????? ?????? ????????? ???????????? ????????????.", Snackbar.LENGTH_SHORT).show()
        }
        binding.btnProfileArchive.setOnClickListener {
            Snackbar.make(binding.root, "??? ?????? ???????????? ?????? ????????? ???????????? ????????????.", Snackbar.LENGTH_SHORT).show()
        }


    }

    private fun observe(adapter: ArchiveAdapter) {
        archiveReader.archives.observe(viewLifecycleOwner) {
            adapter.setArchiveList(it.asArchive())
            checkMyDiaryExist(adapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}