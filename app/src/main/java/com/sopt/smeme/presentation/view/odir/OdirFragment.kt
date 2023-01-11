package com.sopt.smeme.presentation.view.odir

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.business.adaptor.OdirListAdapter
import com.sopt.smeme.business.viewmodel.opendiary.CategoryProvider
import com.sopt.smeme.business.viewmodel.opendiary.OdirListProvider
import com.sopt.smeme.databinding.ChipCategoryBinding
import com.sopt.smeme.databinding.FragmentOdirBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class OdirFragment @Inject constructor(
    @ApplicationContext context: Context,
) : Fragment() {
    private var _binding: FragmentOdirBinding? = null
    private val binding: FragmentOdirBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    private val odirListProvider: OdirListProvider by viewModels()
    private val categoryProvider: CategoryProvider by viewModels()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun constructLayout() {
        // init chip selection to "All"
        binding.chipAllOdir.isChecked = true
        odirListProvider.requestGetList(
            onError = {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        val adapter = OdirListAdapter(requireContext())
        binding.rvDiaryOdir.adapter = adapter
        observe(adapter)

        categoryProvider.getCategories(
            onError = {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun listen() {
        binding.chipsCategoryOdir.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChip: Chip = group.findViewById(group.checkedChipId)
//            selectedChip.let {
//                Snackbar.make(binding.root, selectedChip.text, Snackbar.LENGTH_SHORT).show()
//            } ?: kotlin.run {
//            }
            odirListProvider.requestGetList(
                selectedChip.tag?.toString(),
                onError = {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    fun observe(adapter: OdirListAdapter) {
        odirListProvider.diaries.observe(viewLifecycleOwner) {
            adapter.setOdirList(it.diaries)
        }
        categoryProvider.categories.observe(viewLifecycleOwner) {
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
}