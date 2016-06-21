/**
 * 
 * 
 * 
 * Based in work by Wolf Paulus
 */

#include <SPI.h>
#include "nRF24L01.h"
#include "RF24.h"
int msg[1];
RF24 radio(7,8);
const uint64_t pipe = 0xE8E8F0F0E1LL;


unsigned int encodeAscii(char c);
 
const int ledPin = 13; // built-in LED
const int outPin = 6; // PWM Port OUT
const int inPin  = 0;  // Light Sensor IN 

const char STX = '?';  // Start Token 
const char ETX = '@';  // End Token

const unsigned int SAMPLE_RATE = 44100; // Hz
const int SAMPLES = 512 ; // Num of Samples (128,256,512,1024)
const int REPEAT = 3;     // prolong the signal
const int OFFSET = 60;    // 60 un-usable buckets on the spectrum's lower end

const double FREQ_RES = (double) SAMPLE_RATE / (double) SAMPLES; // 44100/512 = 86.13 freq per bucket
const int DURATION = (int) REPEAT * (1000 / FREQ_RES); // about 35ms
const int ENC_ETX = encodeAscii(ETX); // pre-calc for later use in loop

unsigned int frq[6]; // global frequency array, changes for every run

void setup() {
  pinMode(ledPin, OUTPUT);
  pinMode(outPin, OUTPUT);  
  pinMode(inPin,  INPUT);
  Serial.begin(9600);  // debugging on

  radio.begin();
  radio.openReadingPipe(1,pipe);
  radio.startListening();
  //pinMode(LED1, OUTPUT);}
}

/**
 * Encode the given int value into an frequency array
 * Wrap the message into Start and End tags
 */
void encodeInt(int m) {
  char message[5];
  itoa(m,message,10);

  frq[0] = encodeAscii(STX);   
  for (int i=0; i<5; i++) {
    if ('\0'==message[i]) { // replace '\0' marker w/ ETX
      frq[i+1] = encodeAscii(ETX);   
      break;
    }
    frq[i+1] = encodeAscii(message[i]); 
  }
}

/**
 * Encode a single character: 
 * ACSCII range is about 45 .. 100
 * Unusable range is 60, therefore
 * ((5 * (c-45) + 60) * FREQ_RES)
 */
unsigned int encodeAscii(char c) {
  return (unsigned int) ((5 * (c-45) + OFFSET) * FREQ_RES);
}

void loop() {
  
  if (radio.available()){    
   while (radio.available()){
     radio.read(msg, 1);      
     Serial.println(msg[0]);
     encodeInt(msg[0]);
     if (msg[0] == 111){
     delay(10);
     //digitalWrite(LED1, HIGH);
     Serial.println("high");
     }
     else {
     //digitalWrite(LED1, LOW);
     Serial.println("LOW");}
     delay(10);
     }
     }

  digitalWrite(ledPin, HIGH);

  int i=0;
  do { // loop through the frequency array    
    tone(outPin, frq[i]);
    delay(DURATION);   
    noTone(outPin);
  } while (frq[i++]!=ENC_ETX);

  digitalWrite(ledPin, LOW); 
  delay(500); 
}  

