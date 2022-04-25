import string
import threading
from types import resolve_bases
from sense_hat import SenseHat
from timeit import default_timer as timer
import sense_hat
import requests
import json
import gps 
from time import sleep
from sense_hat.stick import ACTION_PRESSED

sense = SenseHat()
session = gps.gps("localhost", "2947")
session.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)
time = 0
init_speed = 0
curr_speed = 0
is_running = False
OFFSET_LEFT = 1
OFFSET_TOP = 2

# Displays a single digit (0-9)
def show_digit(val, xd, yd, r, g, b):
  offset = val * 15
  for p in range(offset, offset + 15):
    xt = p % 3
    yt = (p-offset) // 3
    sense.set_pixel(xt+xd, yt+yd, r*NUMS[p], g*NUMS[p], b*NUMS[p])




# Displays a two-digits positive number (0-99)
def show_number(val, r, g, b):
  abs_val = abs(val)
  tens = abs_val // 10
  units = abs_val % 10
  if (abs_val > 9): show_digit(tens, OFFSET_LEFT, OFFSET_TOP, r, g, b)
  show_digit(units, OFFSET_LEFT+4, OFFSET_TOP, r, g, b)

#function for the sense hat acceleration monitoring
def acceleration():
    sense.clear()   
    print("Start or stop")     
    if is_running:      
        while is_running:
            start = timer()
            global init_speed
            accel = sense.get_accelerometer_raw()
            end = timer()
            T = (end-start)
            curr_speed = (init_speed + ((accel['x'] * 9.81) * T)) * 2.237
            # print(curr_speed)
            init_speed = round(curr_speed)
            show_number(round(curr_speed), 255, 255, 255)               
            print(curr_speed)
            sleep(0.4)
    else:
        print("stopping accel") 

#function for the gps monitoring
def gps():
    with open("route.json", "a") as out_file:
        out_file.truncate(0)
        out_file.write("[")
        while is_running:
            try:
                global session
                report = session.next()
                if report['class'] == 'TPV':
                    if hasattr(report, 'time'):
                        time = report.time
                        print(time)
                    if hasattr(report, 'lat'):
                        lat = report.lat
                        print(lat)
                    if hasattr(report, 'lon'):
                        lon = report.lon
                        print(lon)
                    print(report)
                    route = {                        
                        "lat": lat,
                        "lng": lon,
                        "time": time
                    }                              
                    json.dump(route, out_file, indent = True)
                    out_file.write(", \n")                              
                    sleep(6)
            except KeyError:
                pass
            except KeyboardInterrupt:
                quit()
            except StopIteration:
                session = None
                print("GPSD has terminated")       
        out_file.truncate(out_file.tell() - 3)        
        out_file.write("]")

#this will be used to create the route
#currently not working due to gps module so new user is here instead
def up(event):
    sense.clear()
    if event.action == ACTION_PRESSED:
        #get the data from the route json file
        route = open("route.json", "r")
        data = json.load(route)
        response = requests.post('http://localhost:8080/routeservice/create', json=data)
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
    sense.clear()
    global is_running
    t1 = threading.Thread(target=acceleration)
    t2 = threading.Thread(target=gps)    
        #will run on the button press not release or hold
    if event.action == ACTION_PRESSED:
        if is_running == False:
            is_running = True
            t1.start()
            t2.start()
            print("Threads started")
        else:
            is_running = False
            print("Threads stopped")
                             

sense.stick.direction_up = up
sense.stick.direction_right = right
sense.stick.direction_left = left
sense.stick.direction_down = down
sense.stick.direction_middle = start
#green color
g = [0, 255, 0]
#numbers
NUMS =[1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,  # 0
       0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,  # 1
       1,1,1,0,0,1,0,1,0,1,0,0,1,1,1,  # 2
       1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,  # 3
       1,0,0,1,0,1,1,1,1,0,0,1,0,0,1,  # 4
       1,1,1,1,0,0,1,1,1,0,0,1,1,1,1,  # 5
       1,1,1,1,0,0,1,1,1,1,0,1,1,1,1,  # 6
       1,1,1,0,0,1,0,1,0,1,0,0,1,0,0,  # 7
       1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,  # 8
       1,1,1,1,0,1,1,1,1,0,0,1,0,0,1]  # 9
#display image
ready_image = [
    g,g,g,g,g,g,g,g,
    g,g,g,g,g,g,g,g,
    g,g,g,g,g,g,g,g,
    g,g,g,g,g,g,g,g,   
    g,g,g,g,g,g,g,g,
    g,g,g,g,g,g,g,g,
    g,g,g,g,g,g,g,g,
    g,g,g,g,g,g,g,g
]

while True:
    #set all the pixels on the sense hat to green       
    if is_running == False:
        sense.set_pixels(ready_image)      
    pass

