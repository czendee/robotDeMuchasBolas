/*
   === Arduino Mecanum Wheels Robot ===
     Smartphone control via Bluetooth 
  by Dejan, www.HowToMechatronics.com
  Libraries:
  RF24, https://github.com/tmrh20/RF24/
  AccelStepper by Mike McCauley: http://www.airspayce.com/mikem/arduino/AccelStepper/index.html

*/

#include <SoftwareSerial.h>
#include <AccelStepper.h>

SoftwareSerial Bluetooth(A8, 38); // Arduino(RX, TX) - HC-05 Bluetooth (TX, RX)

// Define the stepper motors and the pins the will use
AccelStepper LeftBackWheel(1, 42, 43);   // (Type:driver, STEP, DIR) - Stepper1
AccelStepper LeftFrontWheel(1, 40, 41);  // Stepper2
AccelStepper RightBackWheel(1, 44, 45);  // Stepper3
AccelStepper RightFrontWheel(1, 46, 47); // Stepper4

#define led 14

int wheelSpeed = 1500;

int dataIn, m;

int lbw[50], lfw[50], rbw[50], rfw[50]; // for storing positions/steps
int index = 0;
//TODO: define array for the role CURRENT
int currentRoles[50]; // role CURRENT
//TODO:define array for the tasks of a role CURRENT
int currentRoleTasks[50][50]; // roleTasks CURRENT
//TODO:define array for the steps of a task CURRENT
int currentTaskStep[50][50]; // taskSteps CURRENT
//TODO:define arrays for the positions of a step CURRENT
int currentStepPosition[50][4]; // stepPosition CURRENT One for lbw, lfw, rbw, rfw , each wheel 
//TODO: define array for the role OBTAINED FROM WEB MOST RECENT
int webMostRecentRoles[50]; // role WEB MOST RECENT
//TODO:define array for the tasks of a role  OBTAINED FROM WEB MOST RECENT
int webMostRecentRoleTasks[50][50]; // roleTasks WEB MOST RECENT
//TODO:define array for the steps of a task  OBTAINED FROM WEB MOST RECENT
int webMostRecentTaskStep[50][50]; // taskSteps WEB MOST RECENT
//TODO:define arrays for the positions of a step  OBTAINED FROM WEB MOST RECENT
int webMostRecentStepPosition[50][4]; // stepPosition WEB MOST RECENT One for lbw, lfw, rbw, rfw , each wheel 

//TODO: loop
   int currentTask =1;//default Task
   int currentRole =1;//default Role
//TODO: logic to check if there is conection, then read all the most recent information from web API, and overwrite the 
//TODO: logic to check if CURRENT is equal to OBTAINED FROM WEB MOST RECENT,  then continue with the task being executed
//TODO: logic to check if CURRENT is NOT equal to OBTAINED FROM WEB MOST RECENT,then  complete the current task being executed, if any, then when it is completed set CURRENT arrays with the
//         content of the arrays OBTAINED FROM WEB MOST RECENT



void setup() {
  // Set initial seed values for the steppers
  LeftFrontWheel.setMaxSpeed(3000);
  LeftBackWheel.setMaxSpeed(3000);
  RightFrontWheel.setMaxSpeed(3000);
  RightBackWheel.setMaxSpeed(3000);

  Serial.begin(38400);
  Bluetooth.begin(38400); // Default baud rate of the Bluetooth module
  Bluetooth.setTimeout(1);
  delay(20);

  pinMode(led, OUTPUT);

}

void loop() {
  // Check for incoming data

  if (Bluetooth.available() > 0) {
    dataIn = Bluetooth.read();  // Read the data

    if (dataIn == 0) {
      m = 0;
    }
    if (dataIn == 1) {
      m = 1;
    }
    if (dataIn == 2) {
      m = 2;
    }
    if (dataIn == 3) {
      m = 3;
    }
    if (dataIn == 4) {
      m = 4;
    }
    if (dataIn == 5) {
      m = 5;
    }
    if (dataIn == 6) {
      m = 6;
    }
    if (dataIn == 7) {
      m = 7;
    }
    if (dataIn == 8) {
      m = 8;

    }
    if (dataIn == 9) {
      m = 9;
    }
    if (dataIn == 10) {
      m = 10;
    }
    if (dataIn == 11) {
      m = 11;
    }

    if (dataIn == 12) {
      m = 12;
    }
    if (dataIn == 14) {
      m = 14;
    }
    // Set speed
    if (dataIn >= 16) {
      wheelSpeed = dataIn * 10;
      Serial.println(wheelSpeed);
    }
     
    // YOUTOCHI:More Recent WEB: Role
    if (dataIn == 30) {
      m = 30;
    }
    if (dataIn == 31) {
      m = 31;
    }
    if (dataIn == 32) {
      m = 32;
    }     
     // YOUTOCHI:More Recent WEB: Task
    if (dataIn == 40) {
      m = 40;
    }
    if (dataIn == 41) {
      m = 41;
    }
    if (dataIn == 42) {
      m = 42;
    }          

  }
  if (m == 4) {
    moveSidewaysLeft();
  }
  if (m == 5) {
    moveSidewaysRight();
  }
  if (m == 2) {
    moveForward();
  }
  if (m == 7) {
    moveBackward();
  }
  if (m == 3) {
    moveRightForward();
  }
  if (m == 1) {
    moveLeftForward();
  }
  if (m == 8) {
    moveRightBackward();
  }
  if (m == 6) {
    moveLeftBackward();
  }
  if (m == 9) {
    rotateLeft();
  }
  if (m == 10) {
    rotateRight();
  }

  if (m == 0) {
    stopMoving();
  }
  //Serial.println(dataIn);
  // If button "SAVE" is pressed
  if (m == 12) {
    if (index == 0) {
      LeftBackWheel.setCurrentPosition(0);
      LeftFrontWheel.setCurrentPosition(0);
      RightBackWheel.setCurrentPosition(0);
      RightFrontWheel.setCurrentPosition(0);
    }
    lbw[index] = LeftBackWheel.currentPosition();  // save position into the array
    lfw[index] = LeftFrontWheel.currentPosition();
    rbw[index] = RightBackWheel.currentPosition();
    rfw[index] = RightFrontWheel.currentPosition();
    index++;                        // Increase the array index
    m = 0;
  }

  if (m == 14) {
    runSteps();
    if (dataIn != 14) {
      stopMoving();
      memset(lbw, 0, sizeof(lbw)); // Clear the array data to 0
      memset(lfw, 0, sizeof(lfw));
      memset(rbw, 0, sizeof(rbw));
      memset(rfw, 0, sizeof(rfw));
      index = 0;  // Index to 0
    }
  }

   
  LeftFrontWheel.runSpeed();
  LeftBackWheel.runSpeed();
  RightFrontWheel.runSpeed();
  RightBackWheel.runSpeed();

  // Monitor the battery voltage
  int sensorValue = analogRead(A0);
  float voltage = sensorValue * (5.0 / 1023.00) * 3; // Convert the reading values from 5v to suitable 12V i
  //Serial.println(voltage);
  // If voltage is below 11V turn on the LED
  if (voltage < 11) {
    digitalWrite(led, HIGH);
  }
  else {
    digitalWrite(led, LOW);
  }

}

void runSteps() {
  for (int i = index - 1; i >= 0; i--) { // Run through all steps(index)
    LeftFrontWheel.moveTo(lfw[i]);
    LeftFrontWheel.setSpeed(wheelSpeed);
    LeftBackWheel.moveTo(lbw[i]);
    LeftBackWheel.setSpeed(wheelSpeed);
    RightFrontWheel.moveTo(rfw[i]);
    RightFrontWheel.setSpeed(wheelSpeed);
    RightBackWheel.moveTo(rbw[i]);
    RightBackWheel.setSpeed(wheelSpeed);

    while (LeftBackWheel.currentPosition() != lbw[i] & LeftFrontWheel.currentPosition() != lfw[i] & RightFrontWheel.currentPosition() != rfw[i] & RightBackWheel.currentPosition() != rbw[i]) {
      LeftFrontWheel.runSpeedToPosition();
      LeftBackWheel.runSpeedToPosition();
      RightFrontWheel.runSpeedToPosition();
      RightBackWheel.runSpeedToPosition();

      if (Bluetooth.available() > 0) {      // Check for incomding data
        dataIn = Bluetooth.read();
        if ( dataIn == 15) {           // If button "PAUSE" is pressed
          while (dataIn != 14) {         // Wait until "RUN" is pressed again
            if (Bluetooth.available() > 0) {
              dataIn = Bluetooth.read();
              if ( dataIn == 13) {
                stopMoving();
                break;
              }
            }
          }
        }
        if (dataIn >= 16) {
          wheelSpeed = dataIn * 10;
          dataIn = 14;
        }
        if ( dataIn == 13) {
          break;
        }
      }
    }
  }
  // Go back through steps
  for (int i = 1; i <= index - 1; i++) { // Run through all steps(index)

    LeftFrontWheel.moveTo(lfw[i]);
    LeftFrontWheel.setSpeed(wheelSpeed);
    LeftBackWheel.moveTo(lbw[i]);
    LeftBackWheel.setSpeed(wheelSpeed);
    RightFrontWheel.moveTo(rfw[i]);
    RightFrontWheel.setSpeed(wheelSpeed);
    RightBackWheel.moveTo(rbw[i]);
    RightBackWheel.setSpeed(wheelSpeed);

    while (LeftBackWheel.currentPosition() != lbw[i]& LeftFrontWheel.currentPosition() != lfw[i] & RightFrontWheel.currentPosition() != rfw[i] & RightBackWheel.currentPosition() != rbw[i]) {

      LeftFrontWheel.runSpeedToPosition();
      LeftBackWheel.runSpeedToPosition();
      RightFrontWheel.runSpeedToPosition();
      RightBackWheel.runSpeedToPosition();
      //Serial.print("  current: ");
      //Serial.println(LeftBackWheel.currentPosition());

      if (Bluetooth.available() > 0) {      // Check for incomding data
        dataIn = Bluetooth.read();
        if ( dataIn == 15) {           // If button "PAUSE" is pressed
          while (dataIn != 14) {         // Wait until "RUN" is pressed again
            if (Bluetooth.available() > 0) {
              dataIn = Bluetooth.read();
              if ( dataIn == 13) {
                stopMoving();
                break;
              }
            }
          }
        }
        if (dataIn >= 16) {
          wheelSpeed = dataIn * 10;
          dataIn = 14;
        }
        if ( dataIn == 13) {
          //Serial.println("DEKI");
          break;
        }
      }
    }
  }
}

        
//YOUTOCHi// runs steps for the current task and current role 
void runStepsForTheTaskRole() {

  for (int i = index - 1; i >= 0; i--) { // Run through all steps(index)
    LeftFrontWheel.moveTo(webMostRecentStepPosition[currentRole][currentTask][i][0]); //lfw[i]); 
    LeftFrontWheel.setSpeed(wheelSpeed);
    LeftBackWheel.moveTo(webMostRecentStepPosition[currentRole][currentTask][i][1]); //lbw[i]);
    LeftBackWheel.setSpeed(wheelSpeed);
    RightFrontWheel.moveTo(webMostRecentStepPosition[currentRole][currentTask][i][2]);// rfw[i]);
    RightFrontWheel.setSpeed(wheelSpeed);
    RightBackWheel.moveTo(webMostRecentStepPosition[currentRole][currentTask][i][3]); //rbw[i]);
    RightBackWheel.setSpeed(wheelSpeed);

    while (LeftBackWheel.currentPosition() != webMostRecentStepPosition[currentRole][currentTask][i][1] &
           LeftFrontWheel.currentPosition() != webMostRecentStepPosition[currentRole][currentTask][i][0]
           & RightFrontWheel.currentPosition() != webMostRecentStepPosition[currentRole][currentTask][i][2] &
           RightBackWheel.currentPosition() != webMostRecentStepPosition[currentRole][currentTask][i][3]) {
    //while (LeftBackWheel.currentPosition() != lbw[i] & LeftFrontWheel.currentPosition() != lfw[i] & RightFrontWheel.currentPosition() != rfw[i] & RightBackWheel.currentPosition() != rbw[i]) {       
      LeftFrontWheel.runSpeedToPosition();
      LeftBackWheel.runSpeedToPosition();
      RightFrontWheel.runSpeedToPosition();
      RightBackWheel.runSpeedToPosition();

      if (Bluetooth.available() > 0) {      // Check for incomding data
        dataIn = Bluetooth.read();
        if ( dataIn == 15) {           // If button "PAUSE" is pressed
          while (dataIn != 14) {         // Wait until "RUN" is pressed again
            if (Bluetooth.available() > 0) {
              dataIn = Bluetooth.read();
              if ( dataIn == 13) {
                stopMoving();
                break;
              }
            }
          }
        }//end if 15 
        if (dataIn >= 16) {
          wheelSpeed = dataIn * 10;
          dataIn = 14;
        }
        if ( dataIn == 13) {
          break;
        }
      // YOUTOCHI:More Recent WEB: Role
       if (dataIn >= 30) {
          if (dataIn == 30) {//new Role  in the WEB for the Robot 
               flagNewRoles = 1;
          }
          if (dataIn == 31) {
               flagNewRoles = 2;
          }
          if (dataIn == 32 {
               flagNewRoles = 3;
          }       
       }     
       // More Recent WEB: Task
       if (dataIn >= 40) {
          if (dataIn == 40) {//new Task in the WEB for the Robot 
               flagNewTask = 1;
          }
          if (dataIn == 31) {
               flagNewTask = 2;
          }
          if (dataIn == 32 {
               flagNewTask = 3;
          }       
       }              
       if (currentTask == flagNewTask {

       }else{//mark that as soon as the current task is completed, it has to be changed
            flagChangeTask=1;

       }

       if (currentRole == flagNewRole {

       }else{//mark that as soon as the current task is completed, it has to be changed 
          flagChangeRole=1;
       }

    // YOUTOCHI:END   ---More Recent WEB: Role
         
      }//end if blue
    }//end while
  }//end for
           
  if(flagChangeTask==1){
     currentTask=flagNewTask;
  }
  if(flagChangeRole==1){
     currentRole=flagNewRole;
  }
        
        
//YOUTOCHi   END
        
void moveForward() {
  LeftFrontWheel.setSpeed(wheelSpeed);
  LeftBackWheel.setSpeed(wheelSpeed);
  RightFrontWheel.setSpeed(wheelSpeed);
  RightBackWheel.setSpeed(wheelSpeed);
}
void moveBackward() {
  LeftFrontWheel.setSpeed(-wheelSpeed);
  LeftBackWheel.setSpeed(-wheelSpeed);
  RightFrontWheel.setSpeed(-wheelSpeed);
  RightBackWheel.setSpeed(-wheelSpeed);
}
void moveSidewaysRight() {
  LeftFrontWheel.setSpeed(wheelSpeed);
  LeftBackWheel.setSpeed(-wheelSpeed);
  RightFrontWheel.setSpeed(-wheelSpeed);
  RightBackWheel.setSpeed(wheelSpeed);
}
void moveSidewaysLeft() {
  LeftFrontWheel.setSpeed(-wheelSpeed);
  LeftBackWheel.setSpeed(wheelSpeed);
  RightFrontWheel.setSpeed(wheelSpeed);
  RightBackWheel.setSpeed(-wheelSpeed);
}
void rotateLeft() {
  LeftFrontWheel.setSpeed(-wheelSpeed);
  LeftBackWheel.setSpeed(-wheelSpeed);
  RightFrontWheel.setSpeed(wheelSpeed);
  RightBackWheel.setSpeed(wheelSpeed);
}
void rotateRight() {
  LeftFrontWheel.setSpeed(wheelSpeed);
  LeftBackWheel.setSpeed(wheelSpeed);
  RightFrontWheel.setSpeed(-wheelSpeed);
  RightBackWheel.setSpeed(-wheelSpeed);
}
void moveRightForward() {
  LeftFrontWheel.setSpeed(wheelSpeed);
  LeftBackWheel.setSpeed(0);
  RightFrontWheel.setSpeed(0);
  RightBackWheel.setSpeed(wheelSpeed);
}
void moveRightBackward() {
  LeftFrontWheel.setSpeed(0);
  LeftBackWheel.setSpeed(-wheelSpeed);
  RightFrontWheel.setSpeed(-wheelSpeed);
  RightBackWheel.setSpeed(0);
}
void moveLeftForward() {
  LeftFrontWheel.setSpeed(0);
  LeftBackWheel.setSpeed(wheelSpeed);
  RightFrontWheel.setSpeed(wheelSpeed);
  RightBackWheel.setSpeed(0);
}
void moveLeftBackward() {
  LeftFrontWheel.setSpeed(-wheelSpeed);
  LeftBackWheel.setSpeed(0);
  RightFrontWheel.setSpeed(0);
  RightBackWheel.setSpeed(-wheelSpeed);
}
void stopMoving() {
  LeftFrontWheel.setSpeed(0);
  LeftBackWheel.setSpeed(0);
  RightFrontWheel.setSpeed(0);
  RightBackWheel.setSpeed(0);
}
