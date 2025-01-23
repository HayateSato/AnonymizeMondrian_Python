//package com.example.pythoncalculation;
//
//
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.ViewCompat;
//import com.chaquo.python.android.AndroidPlatform;
//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//
//public class MainActivity extends AppCompatActivity {
//
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
//    public void onAnonymizeButtonClick(View view) {
//        try {
//            // Initialize Python instance
//            Python python = Python.getInstance();
//
//            // Define the parameters required by the Python function
//            String dataFilePath = getFilesDir() + "/dataset.csv"; // Adjust the path to your dataset
//            String hierarchyDir = getFilesDir() + "/assets/hierarchy/"; // Adjust the path to hierarchy files
//            int kValue = 5;
//
//            // Pass arguments to the Python function
//            PyObject pyObjectResult = python.getModule("mondrian") // Replace with the Python file name (without .py)
//                    .callAttr(
//                            "run_anonymize",
//                            new String[]{"sex", "age", "race", "marital-status", "education", "native-country", "workclass", "occupation"}, // qi_list
//                            new String[]{"salary-class"}, // sensitive_attributes
//                            new String[]{"ID", "soc_sec_id", "given_name", "surname"}, // identifier
//                            dataFilePath,
//                            hierarchyDir,
//                            kValue
//                    );
//
//            // Display success message or anonymized data path
//            textViewOutput.setText("Anonymization completed. Anonymized file saved at:\n" + pyObjectResult.toString());
//        } catch (Exception e) {
//            // Handle exceptions
//            textViewOutput.setText("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////
////import android.os.Bundle;
////import android.view.View;
////import android.widget.TextView;
////
////import androidx.activity.EdgeToEdge;
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.core.graphics.Insets;
////import androidx.core.view.ViewCompat;
////import androidx.core.view.WindowInsetsCompat;
////
////import com.chaquo.python.PyObject;
////import com.chaquo.python.Python;
////import com.chaquo.python.android.AndroidPlatform;
////
////import com.chaquo.python.PyObject;
////import com.chaquo.python.Python;
////
////import java.io.File;
////
////
////
////
////    public class MainActivity extends AppCompatActivity {
////
////        private TextView textViewOutput;
////
////        @Override
////        protected void onCreate(Bundle savedInstanceState) {
////            super.onCreate(savedInstanceState);
////            EdgeToEdge.enable(this);
////            setContentView(R.layout.activity_main);
////            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.csv_button), (v, insets) -> {
////                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////
////                // this assign the calculated values to the "hello world" string of the main page
////                textViewOutput = findViewById(R.id.textViewOutput);
////
////                // requires to incorporate python into the project
////                Python.start(new AndroidPlatform(getApplicationContext()));
////
////                return insets;
////            });
////        }
////
////
////
////
////        // mondrian
//////    public void readButtonPythonRun(View view) {
//////        try {
//////            // Initialize Python instance
//////            Python python = Python.getInstance();
//////
//////            // Call the Python function to read the CSV file
//////            PyObject pyObjectResult = python.getModule("mondrian") // Python file name (without .py)
//////                    .callAttr("run_anonymize"); // function name
//////
//////            // Set the result to the TextView
//////            textViewOutput.setText(pyObjectResult.toString());
//////        } catch (Exception e) {
//////            // Handle any exceptions
//////            textViewOutput.setText("Error: " + e.getMessage());
//////            e.printStackTrace();
//////        }
//////    }
////
////
////
////
////
//////
//////
//////
//////    public void readButtonPythonRun(View view) {
//////        try {
//////            // Initialize Python instance
//////            Python python = Python.getInstance();
//////
//////            // Get the mondrian module
//////            PyObject mondrianModule = python.getModule("mondrian");
//////
//////            // Set up the parameters for the run_anonymize function
//////            PyObject qiList = python.getBuiltins().callAttr("list", new String[]{"sex", "age", "race", "marital-status", "education", "native-country", "workclass", "occupation"});
//////            PyObject sensitiveAttributes = python.getBuiltins().callAttr("list", new String[]{"salary-class"});
//////            PyObject identifiers = python.getBuiltins().callAttr("list", new String[]{"ID", "soc_sec_id", "given_name", "surname"});
//////            String dataFilePath = "assets/dataset.csv";
//////            String hierarchyFileDirPath = "/hierarchy/";
//////            int k = 3;
//////
//////            // Call the run_anonymize function
//////            PyObject result = mondrianModule.callAttr("run_anonymize",
//////                    qiList,
//////                    sensitiveAttributes,
//////                    identifiers,
//////                    dataFilePath,
//////                    hierarchyFileDirPath,
//////                    k
//////            );
//////
//////            // Convert the result to a string and display it
//////            String resultString = result.toString();
//////            textViewOutput.setText("Anonymization completed. Result: " + resultString);
//////        } catch (Exception e) {
//////            // Handle any exceptions
//////            textViewOutput.setText("Error: " + e.getMessage());
//////            e.printStackTrace();
//////        }
//////    }
//////
//////
//////
////
////
////
////
////
////
////
////
////
////        // Reading csv and printing
////        public void readButtonPythonRun(View view) {
////            try {
////                // Initialize Python instance
////                Python python = Python.getInstance();
////
////                // Call the Python function to read the CSV file
////                PyObject pyObjectResult = python.getModule("input_reader") // Python file name (without .py)
////                        .callAttr("get_csvfile", "dataset.csv"); // function name and CSV file path
////
////                // Set the result to the TextView
////                textViewOutput.setText(pyObjectResult.toString());
////            } catch (Exception e) {
////                // Handle any exceptions
////                textViewOutput.setText("Error: " + e.getMessage());
////                e.printStackTrace();
////            }
////        }
////
////
////
//////
//////
//////    public void readButtonPythonRun(View view){
//////        Python python = Python.getInstance();
//////        PyObject pyObjectResult = python.getModule("FILENAME").callAttr("FUINCTION_NAME", 100, 20);
//////        textViewOutput.setText(pyObjectResult.toString());
//////
//////    }
////
////
////
////
////    }