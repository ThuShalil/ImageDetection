import argparse
import pathlib
# from imageai.Prediction import ImagePrediction
from imageai.Detection import ObjectDetection, VideoObjectDetection

import os

version = "0.0.1"

parser = argparse.ArgumentParser()

parser.add_argument('--type', action="store", dest="type",
                    help="type=detect - recognize objects, type=train - train images")
parser.add_argument('--modelpath', action="store", dest="modelpath", help="path to file with trained models")
parser.add_argument("--file", action="store", dest="file",
                    help="path to media file like image/video that you want to predict")
parser.add_argument("--traindirectory", action="store", dest="traindirectory",
                    help="path to directory where traing data are stored")
parser.add_argument("--resoult", action="store", dest="resoult", help="file to store resoults")

parser.add_argument('--version', action='version', version='%(prog)s ' + version)

results = parser.parse_args()

if results.type == "detect":

    closeapp = False

    if results.modelpath is None:
        print("missing arg --modelpath")
        closeapp = True
    if results.file is None:
        print("missing arg --file")
        closeapp = True
    if results.resoult is None:
        print("missing arg --resoult")
        closeapp = True

    if closeapp:
        print("The program will be closed...")
        exit(0)

    # prediction = ImagePrediction()
    # prediction.setModelTypeAsResNet()
    # prediction.setModelPath(results.modelpath)
    # prediction.loadModel()
    # predictions, percentage_probabilities = prediction.predictImage(results.file, result_count=results.predictlimit)

    detector = ObjectDetection()
    detector.setModelTypeAsYOLOv3()
    detector.setModelPath(results.modelpath)
    detector.loadModel()

    filename = os.path.basename(results.file)
    filenamebase = os.path.splitext(results.file)[0]
    filenameext = os.path.splitext(filename)[1]

    detections = detector.detectObjectsFromImage(input_image=results.file,
                                                 output_image_path=filenamebase + "-detection" + filenameext)

    fileres = open(results.resoult + ".csv", "w")

    for eachObject in detections:
        fileres.write("{},{}\n".format(eachObject["name"], eachObject["percentage_probability"]))
        print(eachObject["name"], " : ", eachObject["percentage_probability"])
    fileres.close()
    exit(100)

    # for index in range(len(predictions)):
    #     print(predictions[index], " : ", percentage_probabilities[index])
    #     fileres.write(percentage_probabilities[index])
    #     fileres.close()
    #     exit(100)

elif results.type == "detectvideo":

    detector = VideoObjectDetection()
    detector.setModelTypeAsYOLOv3()
    detector.setModelPath(results.modelpath)
    detector.loadModel()

    video_path = detector.detectObjectsFromVideo(input_file_path=results.file,
                                                 output_file_path=results.file + "traffic_detected.mp4"
                                                 , frames_per_second=25, log_progress=True)
elif results.type == "train":
    print("training...")
else:
    print("Please select correct type")
