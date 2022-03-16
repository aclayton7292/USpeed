from types import resolve_bases
from sense_hat import SenseHat
from timeit import default_timer as timer
import sense_hat
import requests
import json
from time import sleep
from sense_hat.stick import ACTION_PRESSED

sense = SenseHat()
time = 0
init_speed = 0
curr_speed = 0
is_running = False

#this will be used to create the route
#currently not working due to gps module so new user is here instead
def up(event):
    if event.action == ACTION_PRESSED:
        response = requests.post('http://localhost:8080/userservice/create', json={"name": "test", "password": "test"})
        if response.status_code == 200:
            sense.show_message("Success", text_colour=[0, 255, 0])
        else:
            sense.show_message("Failure" + str(response.status_code), text_colour=[255, 0, 0])
        sense.clear
def right():
    sense.clear
def down():
    sense.clear
def left():   
    sense.clear
#funtion used to start the speed monitoring that can be displayed
def start(event):
    global is_running
    
        #will run on the button press not release or hold
    if event.action == ACTION_PRESSED:
        print("Start or stop")
            
        if is_running == False:
            is_running = True
            while is_running:
                start = timer()
                global init_speed
                accel = sense.get_accelerometer_raw()
                end = timer()
                T = (end-start)
                curr_speed = (init_speed + (accel['x'] * T)) * 2.237
                # print(curr_speed)
                init_speed = round(curr_speed)
                print(curr_speed)
                sleep(3)     
        else:
            is_running = False      

            
        
        
                  

sense.stick.direction_up = up
sense.stick.direction_right = right
sense.stick.direction_left = left
sense.stick.direction_down = down
sense.stick.direction_middle = start

while True:
    pass
