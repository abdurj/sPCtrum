# -*- coding: utf-8 -*-
import cv2
import numpy as np

image = cv2.imread("test-250.jpg")

hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV).astype("float32")

h,s,v = cv2.split(hsv)

s= s*200
s = np.clip(s,0,255)


hsv = cv2.merge((h,s,v))

image = cv2.cvtColor(hsv.astype("uint8"), cv2.COLOR_HSV2BGR)

cv2.imshow("HSV", image)

cv2.waitKey(0)