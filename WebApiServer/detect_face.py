import requests


def detect_face_api_with_binary(filename):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true"
    # payload = "{\"url\":\"" + face_url + "\"}"
    photo_name = filename
    payload = open(photo_name, 'rb').read()
    print(type(payload))
    headers = {
        "content-type": "application/octet-stream",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)
    print(r.text)
    try:
        response = json.loads(r.text)
        faceId = response[0]["faceId"]
        print(faceId)
        return faceId
    except:
        return "nullFaceId"


filename = "/home/mustafa/OrangeMirrorWebServerApi/orangeMirror/WebApiServer/upload_folder/photo_54.jpg"
detect_face_api_with_binary(filename)
