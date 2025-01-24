package com.example.pythoncalculation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.chaquo.python.android.AndroidPlatform;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;


// creating a main activity
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextView
        textViewOutput = findViewById(R.id.textViewOutput);

        // Initialize Python environment if not started
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

// read button: using the input_reader.py, this triggers to read the csv file.
    public void readButtonPythonRun(View view) {
        try {
            // Initialize Python instance
            Python python = Python.getInstance();

            // Call the Python function to read the CSV file
            PyObject pyObjectResult = python.getModule("input_reader") // Python file name (without .py)
                    .callAttr("get_csvfile", "dataset.csv"); // function name and CSV file path
            textViewOutput.setText(pyObjectResult.toString());

        } catch (Exception e) {
            // Handle any exceptions
            textViewOutput.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

// anonymize button: using the mondrian.py, this triggers to anonymize the csv file.

    public void onAnonymizeButtonClick(View view) {
        try {
            // Initialize Python instance
            Python python = Python.getInstance();

            // Call the Python function to read the CSV file
            PyObject pyObjectResult = python.getModule("mondrian") // Python file name (without .py)
                    .callAttr("anonymize_execute"); // function name and CSV file path

            Log.d(TAG, "MainActivity: anonymization is scuccesufully completed");
            textViewOutput.setText(pyObjectResult.toString());

        } catch (Exception e) {
            Log.e(TAG, "Error during anonymization", e);
        }
    }
}


