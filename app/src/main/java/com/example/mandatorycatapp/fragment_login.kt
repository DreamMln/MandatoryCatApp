package com.example.mandatorycatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatorycatapp.databinding.FragmentLoginBinding
import com.example.mandatorycatapp.models.AuthentificationLoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class fragment_login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val authentificationLoginViewModel: AuthentificationLoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = auth.currentUser
        if (currentUser != null) {

        }
        //login
        binding.messageView.text = "Current user ${currentUser?.email}"
        binding.signInButton.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }
            //erstatter med authentificationLoginViewModel
            authentificationLoginViewModel.logIn(email, password)
            findNavController().navigate(R.id.action_fragment_login_to_FirstFragment)

            //https://firebase.google.com/docs/auth/android/password-auth
            /*auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //if login is success, navigate to firstfragment - index
                    findNavController().navigate(R.id.action_fragment_login_to_FirstFragment)
                } else {
                    binding.messageView.text = task.exception?.message
                }
            }
             */
        }
        //create a user
        //erstatter med authentificationLoginViewModel
        binding.buttonCreateUser.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }
            authentificationLoginViewModel.registerUser(email, password)
            findNavController().navigate(R.id.action_fragment_login_to_FirstFragment)
            /*auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.messageView.text = "User created. Now please login"
                    // Alternative: goto next fragment (no need to login after register)
                    findNavController().navigate(R.id.action_fragment_login_to_FirstFragment)
                } else {
                    binding.messageView.text = task.exception?.message
                }
            }
             */
            binding.buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}