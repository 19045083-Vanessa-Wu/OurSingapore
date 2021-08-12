package sg.edu.rp.c346.id19045083.oursingapore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    TextView tvEditID, tvEditName, tvEditLocation, tvEditDescription, tvEditStars;
    EditText etEditID, etEditName, etEditLocation, etEditDescription;
    RatingBar rbStars;
    Button btnUpdate, btnDelete, btnCancel;
    Food food;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvEditID = findViewById(R.id.textViewEditID);
        tvEditName = findViewById(R.id.textViewEditName);
        tvEditLocation = findViewById(R.id.textViewEditLocation);
        tvEditDescription = findViewById(R.id.textViewEditDescription);
        tvEditStars = findViewById(R.id.textViewEditStars);

        etEditID = findViewById(R.id.editTextEditID);
        etEditName = findViewById(R.id.editTextEditName);
        etEditLocation = findViewById(R.id.editTextEditLocation);
        etEditDescription = findViewById(R.id.editTextEditDescription);
        rbStars = findViewById(R.id.ratingBarEditStars);

        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("data");

        etEditID.setText(String.valueOf(food.get_id()));
        etEditName.setText(food.getName());
        etEditLocation.setText(food.getLocation());
        etEditDescription.setText(food.getDescription());
        rbStars.setRating(food.getStars());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                // Set the dialog details
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(false);

                // Configure the 'positive' button
                myBuilder.setPositiveButton("Do Not Discard", null);

                // Configure the 'negative' button
                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);

                if (etEditName.getText().toString().isEmpty()||
                        etEditLocation.getText().toString().isEmpty()||
                        etEditDescription.getText().toString().isEmpty()) {
                    Toast.makeText(EditActivity.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    food.setName(etEditName.getText().toString());
                    food.setLocation(etEditLocation.getText().toString());
                    food.setDescription(etEditDescription.getText().toString());
                    food.setStars((int) rbStars.getRating());
                    db.updateFood(food);
                    db.close();
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                // Set the dialog details
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the food named: " + food.getName() + "?");
                myBuilder.setCancelable(false);

                // Configure the 'positive' button
                myBuilder.setPositiveButton("Cancel", null);

                // Configure the 'negative' button
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper db = new DBHelper(EditActivity.this);
                        db.deleteFood(food.get_id());
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }   //onCreate()

}   //Class
