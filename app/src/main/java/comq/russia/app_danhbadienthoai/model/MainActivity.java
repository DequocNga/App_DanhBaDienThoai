package comq.russia.app_danhbadienthoai.model;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import comq.russia.app_danhbadienthoai.R;

public class MainActivity extends AppCompatActivity {
    /*khai bao cac thuoc tinh giao dien*/
    EditText edtName;
    EditText edtTelNumber;
    RadioButton rdtMale;
    RadioButton rdtFemale;
    Button btnAddList;
    ListView lvList;
    ArrayList<Person> arrayList = new ArrayList<>();
    comq.russia.app_danhbadienthoai.adapter.ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect();

        /*su dung arrayadapter de truyen toan bo cac phan tu trong mang hien tai vao giao dien dieu khien, truyen toan bo cac phan tu trong mang vao adapter*/
        arrayAdapter = new comq.russia.app_danhbadienthoai.adapter.ArrayAdapter(this, R.layout.item_list, arrayList);
        /*set adapter cho lisview*/
        lvList.setAdapter(arrayAdapter);
        checkPermissionRequest();
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showSelectDiaglog(position);
            }
        });
    }

    private void checkPermissionRequest() {
        String permission[] = new String[]{
                Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS
        };
        ArrayList<String> listPermission = new ArrayList<>();
        for (String s1 : permission) {
            if (ContextCompat.checkSelfPermission(this, s1) != PackageManager.PERMISSION_GRANTED) {
                listPermission.add(s1);
            }
        }
        /*neu nhu ung dung da duoc cap quyen*/
        if (!listPermission.isEmpty()) {
            ActivityCompat.requestPermissions(this, permission, 1);
        }
    }

    public void connect() {
        edtName = findViewById(R.id.edt_name);
        edtTelNumber = findViewById(R.id.edt_number);
        rdtMale = findViewById(R.id.rdt_male);
        rdtFemale = findViewById(R.id.rdt_female);
        btnAddList = findViewById(R.id.btn_addList);
        lvList = findViewById(R.id.lv_showList);
    }

    public void setClickButtonAddList(View view) {
        if (view.getId() == R.id.btn_addList) {
            String name = edtName.getText().toString().trim();
            String tel = edtTelNumber.getText().toString().trim();
            boolean isFemale;
            if (rdtFemale.isChecked()) {
                isFemale = true;
            } else {
                isFemale = false;
            }
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(tel) || (TextUtils.isEmpty(name) && TextUtils.isEmpty(tel))) {
                Toast.makeText(this, "Enter fill banlk", Toast.LENGTH_LONG).show();
            } else {
                Person person = new Person(name, tel, isFemale);
                arrayList.add(person);
            }
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public void showSelectDiaglog(final int position) {
        /*dialog tai vi tri hien tai (position)*/
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.custom_dialog);
//        Button btnCall = dialog.findViewById(R.id.btn_call);
//        Button btnSend = dialog.findViewById(R.id.btn_send);
//        btnCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intentCall(position);
//            }
//        });
//
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intentSend(position);
//            }
//        });
//        dialog.show();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setMessage("Message");
        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intentCall(position);
            }
        });
        builder.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intentSend(position);
            }
        });
        builder.create().show();


    }

    private void intentSend(int position) {
        String phoneNo=arrayList.get(position).getTelNumber();
//
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(phoneNo, null, message, null, null);
//
//        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("SMS: " + arrayList.get(position).getTelNumber()));
//        startActivity(intent);

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNo));
        smsIntent.putExtra("sms_body", "");
        startActivity(smsIntent);

    }

    private void intentCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+arrayList.get(position).getTelNumber()));//"Call to: " + arrayList.get(position).getTelNumber()));
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }
}
