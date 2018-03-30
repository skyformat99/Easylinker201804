import paho.mqtt.client as mqtt
import time
import json
import threading
from multiprocessing import Process
'''
0: Connection successful
1: Connection refused - incorrect protocol version
2: Connection refused - invalid client identifier
3: Connection refused - server unavailable
4: Connection refused - bad username or password
5: Connection refused - not authorised
6-255: Currently unused.
'''
#IN/DEVICE/DEFAULT_USER/XMRJY11/1522404991788
CLIENT_OPENID="1522404991788"
CLIENT_NAME="Sucheon_Auto_Batch_P"
CLIENT_GROUP="XMRJY11"
client = mqtt.Client(CLIENT_NAME)
data={"feature2":"feature2","feature3":"feature3","std":1.1,"meanLf":3.1,"feature4":"feature4","peakFrequency":"peakFrequency","feature1":"feature1","bandSpectrum":"bandSpectrum","peakPowers":"peakPowers","meanHf":2.1}

def send_data():
    global  client
    client.publish("IN/DEVICE/DEFAULT_USER/"+CLIENT_GROUP+"/"+CLIENT_OPENID, str(data))
    timer = threading.Timer(2.0, send_data)
    timer.start()
def on_disconnect(a,b,c):
    print("on_disconnect",a,b,c)

def on_connect(c, userdata, flags, rc):
    print("connect state:",rc)
    if rc==0:
        print("Connected Success! ")
        client.subscribe("OUT/DEVICE/1521688389380/DEFAULT_GROUP/"+CLIENT_OPENID)
        timer = threading.Timer(2.0, send_data)
        timer.start()
    else:
        print("Connected Failed! ")
        client.disconnect()
def on_message(client, userdata, msg):
    print("Received Data:",msg.payload)

client.on_connect = on_connect
client.on_message = on_message
client.on_disconnect=on_disconnect
client.username_pw_set(CLIENT_OPENID,CLIENT_OPENID)
client.connect("localhost", 1883, 60)
client.loop_forever()