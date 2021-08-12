package sg.edu.rp.c346.id19045083.oursingapore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvFoodName, tvLocation, tvDescription, tvStars;
    EditText etFoodName, etLocation, etDescription;
    RatingBar rbStars;
    Button btnInsert, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFoodName = findViewById(R.id.textViewName);
        tvLocation = findViewById(R.id.textViewLocation);
        tvDescription = findViewById(R.id.textViewDescription);
        tvStars = findViewById(R.id.textViewStars);

        etFoodName = findViewById(R.id.editTextName);
        etLocation = findViewById(R.id.editTextLocation);
        etDescription = findViewById(R.id.editTextDescription);

        rbStars = findViewById(R.id.ratingBarStars);

        btnInsert = findViewById(R.id.buttonInsert);
        btnShowList = findViewById(R.id.buttonShowList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etFoodName.getText().toString().isEmpty()|| etLocation.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();

                } else {
                    String name = etFoodName.getText().toString();
                    String location = etLocation.getText().toString();
                    String description = etDescription.getText().toString();
                    int stars = (int) rbStars.getRating();

                    DBHelper dbh = new DBHelper(MainActivity.this);
                    long inserted_id = dbh.insertFood(name, location, description, stars);
                    if (inserted_id > 0) {
                        Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                    }
                }

                //Clear Text,
                etFoodName.setText("");
                etLocation.setText("");
                etDescription.setText("");
                rbStars.setRating(0);

                //Hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });

    }
}