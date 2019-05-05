# -*- coding: utf-8 -*-
import cv2
import numpy as np

image = cv2.imread("test-250.jpg")

rb = image.copy()
rb[:,:,1] = 0
cv2.imshow("Image", rb)

cv2.waitKey(0)  