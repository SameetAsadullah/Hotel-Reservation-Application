package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.dataLayer.VolleyCallBack;
import com.sameetasadullah.i180479_i180531.dataLayer.writerAndReader;
import com.sameetasadullah.i180479_i180531.logicLayer.Customer;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.sameetasadullah.i180479_i180531.logicLayer.Vendor;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Screen extends AppCompatActivity {

    String Page;
    ImageView backButton, addDisplayPic;
    CircleImageView dp;
    EditText name,email,contact,card,cnic,address,password;
    RelativeLayout signup_Button;
    HRS hrs;
    Uri imageURI = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        Page = getIntent().getStringExtra("Page");
        backButton = findViewById(R.id.back_button);
        name = findViewById(R.id.Name_text);
        email = findViewById(R.id.Email_text);
        contact = findViewById(R.id.Contact_text);
        cnic = findViewById(R.id.CNIC_text);
        address = findViewById(R.id.Address_text);
        password = findViewById(R.id.Password_text);
        card = findViewById(R.id.Card_text);
        signup_Button = findViewById(R.id.sign_up_button);
        hrs = HRS.getInstance(Register_Screen.this);
        addDisplayPic = findViewById(R.id.add_display_pic);
        dp = findViewById(R.id.display_pic);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        signup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String Cnic=cnic.getText().toString();
                String Email=email.getText().toString();
                String Contact=contact.getText().toString();
                String Address=address.getText().toString();
                String Password=password.getText().toString();
                String Card=card.getText().toString();

                if(Name.equals("") ||Cnic.equals("") ||Password.equals("") ||Card.equals("") ||Address.equals("") ||Contact.equals("") ||Email.equals("")  ){
                    Toast.makeText(Register_Screen.this,"Please Fill All Blocks",Toast.LENGTH_LONG).show();
                }
                else if (imageURI == null) {
                    Toast.makeText(Register_Screen.this,
                            "Please select image", Toast.LENGTH_LONG).show();
                }
                else {
                    if (Page.equals("Customer")){
                        if (!hrs.validateCustomerEmail(Email)){
                            Toast.makeText(Register_Screen.this,"Account with this Email / Phone no Already Exists",Toast.LENGTH_LONG).show();
                        }
                        else {
                            ProgressDialog pd=new ProgressDialog(Register_Screen.this);
                            pd.setMessage("Loading");
                            pd.setCancelable(false);
                            pd.show();

                            hrs.registerCustomer(Name, Email, Password, Address, Contact, Cnic, Card, imageURI, new VolleyCallBack() {
                                @Override
                                public void onSuccess() {
                                    pd.dismiss();
                                    editor.putString("user", "Customer");
                                    editor.putString("email", Email);
                                    editor.putBoolean("loggedIn", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent intent=new Intent(Register_Screen.this,Customer_Choose_Option_Screen.class);
                                    intent.putExtra("email", Email);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
                    else {
                        if (!hrs.validateVendorEmail(Email)){
                            Toast.makeText(Register_Screen.this,"Account with this Email / Phone no Already Exists",Toast.LENGTH_LONG).show();
                        }
                        else {
                            ProgressDialog pd=new ProgressDialog(Register_Screen.this);
                            pd.setMessage("Loading");
                            pd.setCancelable(false);
                            pd.show();
                            hrs.registerVendor(Name,Email,Password,Address,Contact,Cnic,Card,imageURI, new VolleyCallBack(){
                                @Override
                                public void onSuccess() {
                                    pd.dismiss();
                                    editor.putString("user", "Vendor");
                                    editor.putString("email", Email);
                                    editor.putBoolean("loggedIn", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent intent=new Intent(Register_Screen.this,Vendor_Choose_Option_Screen.class);
                                    intent.putExtra("email", Email);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
                }
            }
        });
        addDisplayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            addDisplayPic.setAlpha((float)0);
            imageURI = data.getData();
            dp.setImageURI(imageURI);
        }
    }
}