package com.example.dm1_p_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.dm1_p_android.FirebaseSyncActivity
import com.example.dm1_p_android.R

class ConfiguracionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_configuracion, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val btnSyncFirebase = view.findViewById<Button>(R.id.btnSyncFirebase)
        btnSyncFirebase?.setOnClickListener {
            startActivity(Intent(requireContext(), FirebaseSyncActivity::class.java))
        }
    }
}