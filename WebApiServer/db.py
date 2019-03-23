from flask import Flask, jsonify, json
from pprint import pprint
import requests

db_json = "db.json"


def get_events():
    with open('events.json') as f:
        data = json.load(f)
        f.close()
    return data


def save_events(data):
    with open('events.json', "w") as f:
        f.write(json.dumps(data, sort_keys=False).replace("'", "\""))
        f.close()


def get_user_events(username):
    data = get_events()
    for d in data:
        if(d["user_name"].lower() == username.lower()):
            events = d["events"][0][0]
            return events
    return ""


def get_db():
    with open('db.json') as f:
        data = json.load(f)
        f.close()
    return data


def save_db(data):
    with open('db.json', "w") as f:
        f.write(json.dumps(data, sort_keys=False).replace("'", "\""))
        f.close()


def create_person_events(user_name):
    data = get_events()
    d = {}
    default_events = json.loads(json.dumps(
        data[0]["events"], sort_keys=False).replace("'", "\""))
    d["user_name"] = user_name
    d["events"] = default_events
    data.append(d)
    save_events(data)


def create_person(user_name, home):
    if(check_name_exist(user_name)):
        print("this name exists.")
        return False
    create_person_events(user_name)
    data = get_db()
    # get person_1 VALUE
    default_person = json.loads(json.dumps(
        data["person_1"], sort_keys=False).replace("'", "\""))
    person = default_person
    # PersonGroup Person Create
    personId = create_face_api_personGroup_person(user_name)
    # Person id person idye kayit edilecek
    person["personId"] = personId
    person["user_name"] = user_name
    person["home"] = home
    person_number = len(data.keys()) + 1
    data["person_"+str(person_number)] = person
    save_db(data)
    return True


def update_settings_for_user(username, settings_json):
    if(not(check_name_exist(username))):
        response = {}
        response["Error"] = "user not found"
        return response
    user_json = {}
    data = get_db()
    person_id = ""

    for d in data:
        if(data[d]["user_name"].lower() == username.lower()):
            user_json = data[d]
            person_id = d
            break
    data[person_id]["settings"] = settings_json
    save_db(data)
    return data[person_id]


def update_events_for_user(username, events_json):
    if(not(check_name_exist(username))):
        response = {}
        response["Error"] = "user not found"
        return response
    user_json = {}
    data = get_events()

    for d in data:
        if(d["user_name"].lower() == username.lower()):
            d["events"] = events_json
            user_json = d
            save_events(data)
            return user_json
            break


def get_settings_for_user(user_name):
    user_json = {}
    data = get_db()
    person_id = ""
    for d in data:
        if(data[d]["user_name"].lower() == user_name.lower()):
            user_json = data[d]
            person_id = d
            break
    return data[person_id]


def create_face_api_personGroup_person(user_name):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/persongroups/4567/persons"
    headers = {
        "content-type": "application/json",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    payload = "{\"name\":\"" + user_name + "\"}"
    r = requests.post(url=url, data=payload, headers=headers)
    try:
        response = json.loads(r.text)
        personId = response["personId"]
        return personId
    except:
        return "nullPerson"


def train_face_api_personGroup_person():
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/persongroups/4567/train"
    headers = {
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, headers=headers)


def add_face_face_api_personGroup_person(user_name, face_url):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/persongroups/4567/persons/" + \
        user_name+"/persistedFaces"
    payload = "{\"url\":\"" + face_url + "\"}"
    headers = {
        "content-type": "application/json",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)


def add_face_face_api_personGroup_person_binary(user_name, filename):
    person_id = get_person_id(user_name)
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/persongroups/4567/persons/" + \
        person_id+"/persistedFaces"
    photo_name = filename
    payload = open(photo_name, 'rb').read()
    headers = {
        "content-type": "application/octet-stream",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)


def get_photo_name(user_name):
    return "test.jpg"


def detect_face_api(face_url):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true"
    # payload = "{\"url\":\"" + face_url + "\"}"
    payload = json.dumps({"url": face_url})
    headers = {
        "content-type": "application/json",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)
    try:
        response = json.loads(r.text)
        faceId = response[0]["faceId"]
        return faceId
    except:
        return "nullFaceId"


def identify_face_api(face_url):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/identify"
    faceId = detect_face_api(face_url)
    payload = json.dumps({
        "personGroupId": "4567",
        "faceIds": [
            faceId
        ],
        "maxNumOfCandidatesReturned": 1,
        "confidenceThreshold": 0.5
    }
    )
    headers = {
        "content-type": "application/json",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)
    try:
        response = json.loads(r.text)
        personId = response[0]["candidates"][0]["personId"]
        confidence = float(response[0]["candidates"][0]["confidence"])
        # belli threshold ustunde ise addFace yap
    except:
        return "nullPersonId"
    user_name = get_name(personId)
    return user_name


def get_person_id(username):

    if(not(check_name_exist(username))):
        response = {}
        response["Error"] = "user not found"
        return response
    user_json = {}
    data = get_db()
    person_id = ""

    for d in data:
        if(data[d]["user_name"].lower() == username.lower()):
            user_json = data[d]
            person_id = d
            break
    return person_id


def identify_face_api_with_binary(filename):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/identify"
    faceId = detect_face_api_with_binary(filename)
    payload = json.dumps({
        "personGroupId": "4567",
        "faceIds": [
            faceId
        ],
        "maxNumOfCandidatesReturned": 1,
        "confidenceThreshold": 0.5
    }
    )
    headers = {
        "content-type": "application/json",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)

    try:
        response = json.loads(r.text)
        personId = response[0]["candidates"][0]["personId"]
        confidence = float(response[0]["candidates"][0]["confidence"])
        print(confidence)
        # belli threshold ustunde ise addFace yap
    except:
        return "Person Couldn't Find"
    user_name = get_name(personId)
    return user_name, confidence


def detect_face_api_with_binary(filename):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true"
    # payload = "{\"url\":\"" + face_url + "\"}"
    photo_name = filename
    payload = open(photo_name, 'rb').read()
    headers = {
        "content-type": "application/octet-stream",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)
    try:
        response = json.loads(r.text)
        faceId = response[0]["faceId"]
        return faceId
    except:
        return "nullFaceId"


def get_name(personId):
    data = get_db()
    for d in data:
        d_personId = data[d]["personId"]
        if(d_personId == personId):
            user_name = data[d]["user_name"]
            return user_name
    return "nullName"


def check_name_exist(user_name):
    data = get_db()
    for d in data:
        if(data[d]["user_name"].lower() == user_name.lower()):
            print("DB"+data[d]["user_name"].lower())
            print("user_name"+user_name.lower())
            return True
    return False  # False


# train_face_api_personGroup_person()
# create_person("abc", "mustafa_home")
# print(identify_face_api_with_binary("upload_folder/photo_89.jpg"))
settings_json = {
    "user_name": "enez",
    "settings": [
        {
            "time_module_enable": "true",
            "timeTxtSize": "210.0",
            "hour24_enable": "false"
        },
        {
            "weather_module": "true",
            "weatherTxtSize": "300",
            "celcius_enable": "false"
        }
    ]
}

# update_settings_for_user("enez", settings_json)
#data = get_db()
# print(data)

"""
data["person_1"]["home"] = "home1"

with open('db.json', "w") as f:
    f.write(str(data).replace("'", "\""))
    f.close()

with open('db.json') as f:
    data = json.load(f)

print(data)
"""
