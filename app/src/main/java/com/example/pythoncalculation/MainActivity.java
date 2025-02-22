package com.example.pythoncalculation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.android.AndroidPlatform;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        textViewOutput = findViewById(R.id.textViewOutput);

        // Initialize Python environment if not started
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    // Read button: function in input_reader.py is called here
    public void readButtonPythonRun(View view) {
        try (PyObject pyObjectResult = Python.getInstance()
                .getModule("algorithm.input_reader")
                .callAttr("get_csvfile", "dataset.csv")) {

            // Show result in the frontend
            textViewOutput.setText(pyObjectResult.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error reading CSV file", e);
            textViewOutput.setText(getString(R.string.error_message, e.getMessage()));
        }
    }

    // Anonymize button: triggers anonymizing the CSV file using mondrian.py
    public void onAnonymizeButtonClick(View view, int kValue) {
        try {
            int durationEstimate = 70 / kValue;

            // Show toast messages
            showToast("Anonymization button (K=" + kValue + ") clicked!");
            showToast("Processing in the backend. Please wait for a short moment");
            showToast("It might take " + durationEstimate + "-" + (durationEstimate + 10) + " seconds");
            showToast("Once the anonymization is completed, the result will be shown above");

            // Call the Python function to anonymize the CSV file
            try (PyObject pyObjectAnonymizedDataResult = Python.getInstance()
                    .getModule("algorithm.mondrian")
                    .callAttr("anonymize_execute", kValue)) {

                // Log and show completion message
                Log.d(TAG, "MainActivity: Anonymization successfully completed");
                showToast("Anonymization is completed!");
                textViewOutput.setText(pyObjectAnonymizedDataResult.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error during anonymization", e);
            textViewOutput.setText(getString(R.string.error_message, e.getMessage()));
            showToast("Anonymization failed");
        }
    }

    public void onAnonymizeButtonClick_k2(View view) {
        onAnonymizeButtonClick(view, 2);
    }

    public void onAnonymizeButtonClick_k5(View view) {
        onAnonymizeButtonClick(view, 5);
    }

    public void onAnonymizeButtonClick_k10(View view) {
        onAnonymizeButtonClick(view, 10);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}



//package com.example.pythoncalculation;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.ViewCompat;
//import com.chaquo.python.android.AndroidPlatform;
//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//    private TextView textViewOutput;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize UI components
//        textViewOutput = findViewById(R.id.textViewOutput);
//
//        // Initialize Python environment if not started
//        if (!Python.isStarted()) {
//            Python.start(new AndroidPlatform(this));
//        }
//    }
//
//    // Read button: function in input_reader.py is called here
//    public void readButtonPythonRun(View view) {
//        try {
//
//            // pop up massage to show that the button is clicked
//            Toast toast = Toast.makeText(getApplicationContext(), "Read button clicked!", Toast.LENGTH_SHORT);
//            toast.show();
//
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Call the Python function to read the CSV file
//            PyObject pyObjectResult = python.getModule("algorithm.input_reader") // directoy.Python_file_name (without.py)
//                    .callAttr("get_csvfile", "dataset.csv"); // function name and input (CSV file name)
//
//            // passing the result from python to Java, showing it in the frontend
//            textViewOutput.setText(pyObjectResult.toString());
//        } catch (Exception e) {
//            // Handle any exceptions
//            textViewOutput.setText("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//
//    // Anonymize button: triggers anonymizing the CSV file using mondrian.py
//    public void onAnonymizeButtonClick_k2(View view) {
//        try {
//            Integer K_VALUE = 2;
//            Integer durationEstimate = 70 / K_VALUE;
//
//            // pop up massage to show that the button is clicked
//            Toast toast1 = Toast.makeText(getApplicationContext(), "Anonymization button (K=" + K_VALUE + ") clicked!", Toast.LENGTH_LONG);
//            toast1.show();
//            Toast toast2 = Toast.makeText(getApplicationContext(), "Processing in the backend. Please wait for a short moment", Toast.LENGTH_LONG);
//            toast2.show();
//            Toast toast3 = Toast.makeText(getApplicationContext(), "It might take " + durationEstimate + "-" + (durationEstimate + 10 ) + " seconds", Toast.LENGTH_LONG);
//            toast3.show();
//            Toast toast3_1 = Toast.makeText(getApplicationContext(), "Once the anonymization is completed, the result will be shown above", Toast.LENGTH_LONG);
//            toast3_1.show();
//
//            // Initialize Python instance
//            Python python = Python.getInstance();
//            // Call the Python function to anonymize the CSV file
//            PyObject pyObjectAnonymizedDataResult = python.getModule("algorithm.mondrian") // directoy.Python_file_name (without.py)
//                    .callAttr("anonymize_execute", K_VALUE);  // function name in the python file
//            // log the anonymization status
//            Log.d(TAG, "MainActivity: Anonymization successfully completed");
//            // pop up massage to show that the anonymization is completed
//            Toast toast4 = Toast.makeText(getApplicationContext(), "Anonymization is completed!", Toast.LENGTH_SHORT);
//            toast4.show();
//            // passing the result from python to Java, showing it in the frontend
//            textViewOutput.setText(pyObjectAnonymizedDataResult.toString());
//
//
//            // Flash memory: Clear or reset variables/resources
//            K_VALUE = null; // Clear the K_VALUE variable
//            pyObjectAnonymizedDataResult = null; // Clear the result object
//
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error during anonymization", e);
//            textViewOutput.setText("Error: " + e.getMessage());
//            Toast toast5 = Toast.makeText(getApplicationContext(), "Anonymization failed", Toast.LENGTH_LONG);
//            toast5.show();
//        }
//    }
//
//
//        public void onAnonymizeButtonClick_k5(View view) {
//            try {
//                Integer K_VALUE = 5;
//                Integer durationEstimate = 70 / K_VALUE;
//
//                // pop up massage to show that the button is clicked
//                Toast toast1 = Toast.makeText(getApplicationContext(), "Anonymization button (K=" + K_VALUE + ") clicked!", Toast.LENGTH_LONG);
//                toast1.show();
//                Toast toast2 = Toast.makeText(getApplicationContext(), "Processing in the backend. Please wait for a short moment", Toast.LENGTH_LONG);
//                toast2.show();
//                Toast toast3 = Toast.makeText(getApplicationContext(), "It might take "  +  durationEstimate + "-" + (durationEstimate + 10 ) +" seconds", Toast.LENGTH_LONG);
//                toast3.show();
//                Toast toast3_1 = Toast.makeText(getApplicationContext(), "Once the anonymization is completed, the result will be shown above", Toast.LENGTH_LONG);
//                toast3_1.show();
//
//                // Initialize Python instance
//                Python python = Python.getInstance();
//                // Call the Python function to anonymize the CSV file
//                PyObject pyObjectAnonymizedDataResult = python.getModule("algorithm.mondrian") // directoy.Python_file_name (without.py)
//                        .callAttr("anonymize_execute", K_VALUE);  // function name in the python file
//                // log the anonymization status
//                Log.d(TAG, "MainActivity: Anonymization successfully completed");
//                // pop up massage to show that the anonymization is completed
//                Toast toast4 = Toast.makeText(getApplicationContext(), "Anonymization is completed!", Toast.LENGTH_SHORT);
//                toast4.show();
//                // passing the result from python to Java, showing it in the frontend
//                textViewOutput.setText(pyObjectAnonymizedDataResult.toString());
//
//
//                // Flash memory: Clear or reset variables/resources
//                K_VALUE = null; // Clear the K_VALUE variable
//                pyObjectAnonymizedDataResult = null; // Clear the result object
//
//
//
//            } catch (Exception e) {
//                Log.e(TAG, "Error during anonymization", e);
//                textViewOutput.setText("Error: " + e.getMessage());
//                Toast toast5 = Toast.makeText(getApplicationContext(), "Anonymization failed", Toast.LENGTH_LONG);
//                toast5.show();
//            }
//    }
//    public void onAnonymizeButtonClick_k10(View view) {
//        try {
//            Integer K_VALUE = 10;
//            Integer durationEstimate = 70 / K_VALUE;
//
//            // pop up massage to show that the button is clicked
//            Toast toast1 = Toast.makeText(getApplicationContext(), "Anonymization button (K=" + K_VALUE + ") clicked!", Toast.LENGTH_LONG);
//            toast1.show();
//            Toast toast2 = Toast.makeText(getApplicationContext(), "Processing in the backend. Please wait for a short moment", Toast.LENGTH_LONG);
//            toast2.show();
//            Toast toast3 = Toast.makeText(getApplicationContext(), "It might take " + durationEstimate + "-" + (durationEstimate + 8 ) + " seconds", Toast.LENGTH_LONG);
//            toast3.show();
//            Toast toast3_1 = Toast.makeText(getApplicationContext(), "Once the anonymization is completed, the result will be shown above", Toast.LENGTH_LONG);
//            toast3_1.show();
//
//            // Initialize Python instance
//            Python python = Python.getInstance();
//            // Call the Python function to anonymize the CSV file
//            PyObject pyObjectAnonymizedDataResult = python.getModule("algorithm.mondrian") // directoy.Python_file_name (without.py)
//                    .callAttr("anonymize_execute", K_VALUE);  // function name in the python file
//            // log the anonymization status
//            Log.d(TAG, "MainActivity: Anonymization successfully completed");
//            // pop up massage to show that the anonymization is completed
//            Toast toast4 = Toast.makeText(getApplicationContext(), "Anonymization is completed!", Toast.LENGTH_SHORT);
//            toast4.show();
//            // passing the result from python to Java, showing it in the frontend
//            textViewOutput.setText(pyObjectAnonymizedDataResult.toString());
//
//
//            // Flash memory: Clear or reset variables/resources
//            K_VALUE = null; // Clear the K_VALUE variable
//            pyObjectAnonymizedDataResult = null; // Clear the result object
//
//
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error during anonymization", e);
//            textViewOutput.setText("Error: " + e.getMessage());
//            Toast toast5 = Toast.makeText(getApplicationContext(), "Anonymization failed", Toast.LENGTH_LONG);
//            toast5.show();
//        }
//    }
//
//}
