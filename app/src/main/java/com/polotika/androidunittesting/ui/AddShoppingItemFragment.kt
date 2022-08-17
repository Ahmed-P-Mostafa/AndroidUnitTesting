package com.polotika.androidunittesting.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.polotika.androidunittesting.R
import com.polotika.androidunittesting.utils.Status
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*

class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeToObservers()
        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }
        btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                name = etShoppingItemName.text.toString(),
                priceString = etShoppingItemPrice.text.toString(),
                amountString = etShoppingItemAmount.text.toString()
            )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObservers() {
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(requireView(), "Shopping Item Added", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(requireView(), "An Error Occurred", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        /*No - OP*/
                    }
                }

            }
        })

        viewModel.curImageUrl.observe(viewLifecycleOwner) {
            Glide.with(requireView()).load(it).into(ivShoppingImage)
        }
    }
}