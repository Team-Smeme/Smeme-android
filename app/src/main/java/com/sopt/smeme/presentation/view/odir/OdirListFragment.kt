package com.sopt.smeme.presentation.view.odir

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.bridge.controller.response.OdirListData
import com.sopt.smeme.R
import com.sopt.smeme.bridge.controller.response.OdirListData
import com.sopt.smeme.business.adaptor.OdirListAdapter
import com.sopt.smeme.business.viewmodel.opendiary.CategoryProvider
import com.sopt.smeme.business.viewmodel.opendiary.OdirListProvider
import com.sopt.smeme.databinding.ChipCategoryBinding
import com.sopt.smeme.databinding.FragmentOdirBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.selects.select
import javax.inject.Inject

@AndroidEntryPoint
class OdirListFragment @Inject constructor(
    @ApplicationContext context: Context,
) : Fragment() {
    private var _binding: FragmentOdirBinding? = null
    private val binding: FragmentOdirBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    private val odirListProvider: OdirListProvider by viewModels()
    private val categoryProvider: CategoryProvider by viewModels()
    private lateinit var adapter: OdirListAdapter

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var diaryIndex = -1

    private lateinit var selectedChip: Chip

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOdirBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constructLayout()
        listen()
    }


    fun constructLayout() {
        // init chip selection to "All"
        selectedChip = binding.chipAllOdir
        selectedChip.apply {
            this.isChecked = true
            this.setTextAppearance(R.style.TextAppearance_Subtitle2)
        }

        odirListProvider.requestGetList(
            onError = {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        adapter = OdirListAdapter(::navigateToDetail, requireContext(), ::updatePosition)
        binding.rvDiaryOdir.adapter = adapter
        observe(adapter)

        categoryProvider.getCategories(
            onError = {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                adapter.plusLikeCount(diaryIndex)
            }
            adapter.resetOdirList(diaryIndex)
        }
    }

    fun listen() {
        binding.chipsCategoryOdir.setOnCheckedStateChangeListener { group, checkedIds ->
            selectedChip.setTextAppearance(R.style.TextAppearance_Body1)
            selectedChip = group.findViewById<Chip?>(group.checkedChipId).apply {
                this.setTextAppearance(R.style.TextAppearance_Subtitle2)
            }

            odirListProvider.requestGetList(
                selectedChip.tag?.toString(),
                onError = {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            )
        }
        binding.btnProfileOdir.setOnClickListener {
            Snackbar.make(binding.root, "??? ?????? ???????????? ?????? ????????? ???????????? ????????????.", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun observe(adapter: OdirListAdapter) {
        odirListProvider.diaries.observe(viewLifecycleOwner) {
            adapter.setOdirList(it.diaries)
        }
        categoryProvider.categories.observe(viewLifecycleOwner) { it ->
            val categoryList = it.categories
            categoryList.forEach {
                val chip = createChip(it.id, it.content)
                binding.chipsCategoryOdir.addView(chip)
            }
        }
    }

    private fun createChip(tag: Int, content: String): Chip {
        val chip = ChipCategoryBinding.inflate(layoutInflater).root
        chip.text = content
        chip.tag = tag

        return chip
    }

    private fun navigateToDetail(id: Int) {
        val intent = Intent(requireContext(), OdirDetailActivity::class.java).apply {
            putExtra("diaryId", id)
        }
        resultLauncher.launch(intent)
    }

    private fun updatePosition(position: Int) {
        diaryIndex = position
    }


}