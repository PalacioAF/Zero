package com.zero.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zero.R;
import com.zero.login.LoginActivity;
import com.zero.model.Estudiante;
import com.zero.request.RequestDeleteNotificacion;
import com.zero.retrofit.ApiRest;
import com.zero.retrofit.Utilities;
import com.zero.sesion_manager.SesionManager;

import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dialog extends DialogFragment {

    SesionManager sesionManager;
    ApiRest mAPIService;

    public Dialog() {
    }

    public static Dialog newInstance(String reason) {
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putString("Reason", reason);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        final String reason = getArguments().getString("Reason");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view;
        android.app.Dialog dialog;
        Button btn_ok;
        final TextView title;
        final TextView detail;


        view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_ok = (Button) view.findViewById(R.id.dialog_btn);
        title = (TextView) view.findViewById(R.id.dialog_title);
        detail = (TextView) view.findViewById(R.id.dialog_detail);
        title.setText("Información");

        //Texto
        switch (reason) {
            case "login":
                detail.setText("Legajo y/o Password Incorrectos");
                break;
            case "logi_api":
                detail.setText("Hubo un error en la conexion");
                break;
            case "login_empty":
                detail.setText("El campo Legajo y/o Password estan vacio");
                break;
            case "api":
                detail.setText("Hubo un error en la conexion");
                break;
            case "authentication":
                detail.setText("Problemas con la autenticación del token, por favor vuelva a loguearse");
                break;
        }

        //evento
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (reason) {
                    case "login":
                        dismiss();
                        break;
                    case "logi_api":
                        dismiss();
                        break;
                    case "login_empty":
                        dismiss();
                        break;
                    case "api":
                        getActivity().finish();
                        break;
                    case "authentication":
                        //sesionManager.logout();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getActivity().startActivity(intent);
                        //getActivity().finish();
                        break;
                }
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }

}
