//package com.example.pythoncalculation;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.ViewCompat;
//import com.chaquo.python.android.AndroidPlatform;
//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//
//
//// creating a main activity
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//    private TextView textViewOutput;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize TextView
//        textViewOutput = findViewById(R.id.textViewOutput);
//        // textViewOutput EditText
//        EditText passwordInput = findViewById(R.id.password_input);
//
//        // Initialize Python environment if not started
//        if (!Python.isStarted()) {
//            Python.start(new AndroidPlatform(this));
//        }
//    }
//
//// read button: using the input_reader.py, this triggers to read the csv file.
//    public void readButtonPythonRun(View view) {
//        try {
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Call the Python function to read the CSV file
//            PyObject pyObjectResult = python.getModule("input_reader") // Python file name (without .py)
//                    .callAttr("get_csvfile", "dataset.csv"); // function name and CSV file path
//            textViewOutput.setText(pyObjectResult.toString());
//
//        } catch (Exception e) {
//            // Handle any exceptions
//            textViewOutput.setText("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//// anonymize button: using the mondrian.py, this triggers to anonymize the csv file.
//
//    public void onAnonymizeButtonClick(View view) {
//        try {
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Call the Python function to read the CSV file
//            PyObject pyObjectResult = python.getModule("mondrian") // Python file name (without .py)
//                    .callAttr("anonymize_execute"); // function name and CSV file path
//
//            Log.d(TAG, "MainActivity: anonymization is scuccesufully completed");
//            textViewOutput.setText(pyObjectResult.toString());
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error during anonymization", e);
//        }
//    }
//
//    public void executePythonScript(String password) {
//        Python py = Python.getInstance();
//        PyObject pyObjectResult = python.getModule("mondrian") // Python file name (without .py)
//                .callAttr("anonymize_execute"); // function name and CSV file path
//        PyObject result = pyObj.callAttr("anonymize_execute", password); // Pass the password here
//
//        // Handle the result if needed
//        System.out.println(result.toString());
//
//    }
//
//
//
////    executeButton.setOnClickListener(view -> {
////        String password = passwordInput.getText().toString(); // Get the password from the input field
////        executePythonScript(password); // Call the function with the password
////    });
//
//
//
//
//
//
//}
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//package com.example.pythoncalculation;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.ViewCompat;
//import com.chaquo.python.android.AndroidPlatform;
//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//
//
//// creating a main activity
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//    private TextView textViewOutput;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize TextView
//        textViewOutput = findViewById(R.id.textViewOutput);
//
//        // Initialize Python environment if not started
//        if (!Python.isStarted()) {
//            Python.start(new AndroidPlatform(this));
//        }
//    }
//
//    // read button: using the input_reader.py, this triggers to read the csv file.
//    public void readButtonPythonRun(View view) {
//        try {
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Call the Python function to read the CSV file
//            PyObject pyObjectResult = python.getModule("input_reader") // Python file name (without .py)
//                    .callAttr("get_csvfile", "dataset.csv"); // function name and CSV file path
//            textViewOutput.setText(pyObjectResult.toString());
//
//        } catch (Exception e) {
//            // Handle any exceptions
//            textViewOutput.setText("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//// anonymize button: using the mondrian.py, this triggers to anonymize the csv file.
//
//    public void onAnonymizeButtonClick(View view) {
//        try {
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Call the Python function to read the CSV file
//            PyObject pyObjectResult = python.getModule("mondrian") // Python file name (without .py)
//                    .callAttr("anonymize_execute"); // function name and CSV file path
//
//            Log.d(TAG, "MainActivity: anonymization is scuccesufully completed");
//            textViewOutput.setText(pyObjectResult.toString());
//
//        } catch (Exception e) {
//            Log.e(TAG, "Error during anonymization", e);
//        }
//    }
//}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.example.pythoncalculation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.chaquo.python.android.AndroidPlatform;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textViewOutput;
//    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        textViewOutput = findViewById(R.id.textViewOutput);
//        passwordInput = findViewById(R.id.password_input); // EditText for password input

        // Initialize Python environment if not started
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    // Read button: triggers reading the CSV file using the input_reader.py script
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

//    public void setPasswordInput(EditText passwordInput) {
////         Initialize Python instance
////            Python python = Python.getInstance();
////
////            // Call the Python function to receive the password from the user
////            PyObject pyObjectPasswordResult = python.getModule("password_receiver") // Python file name (without .py)
////                    .callAttr("get_password", passwordInput);
////            textViewOutput.setText(pyObjectPasswordResult.toString());
//////         Get the password entered by the user
//            String password = passwordInput.getText().toString().trim();
//            Log.d(TAG, "entered password: " + password);
//    }

    // Anonymize button: triggers anonymizing the CSV file using mondrian.py
    public void onAnonymizeButtonClick(View view) {
        try {

            // Initialize Python instance
            Python python = Python.getInstance();
//
//            PyObject pyObjectPasswordResult = python.getModule("password_receiver") // Python file name (without .py)
//                    .callAttr("get_password");
//            textViewOutput.setText(pyObjectPasswordResult.toString());
            // Get the password entered by the user
//            String password = passwordInput.getText().toString().trim();
//            Log.d(TAG, "entered password: " + password);

//            if (password.isEmpty()) {
//                password = " ";
//                textViewOutput.setText("Password is required for anonymization. It will be proceeded with default values");
//                return;
//            }
//            // Initialize Python instance
//            Python python = Python.getInstance();

            // Call the Python function to anonymize the CSV file
            PyObject pyObjectAnonymizedDataResult = python.getModule("mondrian") // Python file name (without .py)
                    .callAttr("anonymize_execute"); // Pass the password to the Python function

            Log.d(TAG, "MainActivity: Anonymization successfully completed");
            textViewOutput.setText(pyObjectAnonymizedDataResult.toString());

        } catch (Exception e) {
            Log.e(TAG, "Error during anonymization", e);
            textViewOutput.setText("Error: " + e.getMessage());
        }
    }
}
