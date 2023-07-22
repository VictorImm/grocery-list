package com.example.grocerylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.grocerylist.databinding.FragmentTypeChooseBinding

class TypeChooseFragment : Fragment() {

    // binding
    private lateinit var binding: FragmentTypeChooseBinding

    // widgets
    private lateinit var btnRaw: ImageButton
    private lateinit var btnClean: ImageButton
    private lateinit var btnWater: ImageButton
    private lateinit var btnSnack: ImageButton
    private lateinit var btnFruit: ImageButton
    private lateinit var btnOther: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTypeChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonListener()
    }

    private fun buttonListener() {
        btnRaw = binding.btnRaw
        btnClean = binding.btnClean
        btnWater = binding.btnWater
        btnSnack = binding.btnSnack
        btnFruit = binding.btnFruit
        btnOther = binding.btnOther

        btnRaw.setOnClickListener {
            val action = TypeChooseFragmentDirections.actionTypeChooseFragmentToGroceryAddFragment(
                type = "Raw"
            )
            this.findNavController().navigate(action)
        }
        btnClean.setOnClickListener{
            val action = TypeChooseFragmentDirections.actionTypeChooseFragmentToGroceryAddFragment(
                type = "Clean"
            )
            this.findNavController().navigate(action)
        }
        btnWater.setOnClickListener{
            val action = TypeChooseFragmentDirections.actionTypeChooseFragmentToGroceryAddFragment(
                type = "Water"
            )
            this.findNavController().navigate(action)
        }
        btnSnack.setOnClickListener{
            val action = TypeChooseFragmentDirections.actionTypeChooseFragmentToGroceryAddFragment(
                type = "Snack"
            )
            this.findNavController().navigate(action)
        }
        btnFruit.setOnClickListener{
            val action = TypeChooseFragmentDirections.actionTypeChooseFragmentToGroceryAddFragment(
                type = "Fruit or Veggies"
            )
            this.findNavController().navigate(action)
        }
        btnOther.setOnClickListener{
            val action = TypeChooseFragmentDirections.actionTypeChooseFragmentToGroceryAddFragment(
                type = "Other"
            )
            this.findNavController().navigate(action)
        }
    }
}