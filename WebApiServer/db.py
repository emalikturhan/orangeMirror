import json
from pprint import pprint
import requests

db_json = "db.json"


def get_db():
    with open('db.json') as f:
        data = json.load(f)
        f.close()
    return data


def save_db(data):
    with open('db.json', "w") as f:
        f.write(str(data).replace("'", "\""))
        f.close()


def create_person(name, home):
    if(check_name_exist(name)):
        print("this name exists.")
        return
    data = get_db()
    # get person_1 VALUE
    default_person = json.loads(str(data["person_1"]).replace("'", "\""))
    person = default_person
    # PersonGroup Person Create
    personId = create_face_api_personGroup_person(name)
    # Person id person idye kayit edilecek
    person["personId"] = personId
    person["name"] = name
    person["home"] = home
    person_number = len(data.keys()) + 1
    data["person_"+str(person_number)] = person
    print(data)
    save_db(data)


def update_settigs_for_user(name, settings_json):
    user_json = {}
    data = get_db()
    person_id = ""
    for d in data:
        if(data[d]["name"].lower() == name.lower()):
            user_json = data[d]
            print("user_json"+str(user_json))
            person_id = d
            break
    data[person_id]["settings"] = settings_json
    print("UPDATED"+str(data[person_id]))
    return data[person_id]


def create_face_api_personGroup_person(name):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/persongroups/4567/persons"
    headers = {
        "content-type": "application/json",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    payload = "{\"name\":\"" + name + "\"}"
    print(str(payload))
    r = requests.post(url=url, data=payload, headers=headers)
    print(r.content)
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


def add_face_face_api_personGroup_person(name, face_url):
    url = "https: // westeurope.api.cognitive.microsoft.com/face/v1.0/persongroups/4567/persons/" + \
        name+"/persistedFaces"
    payload = "{\"url\":\"" + face_url + "\"}"
    headers = {
        "content-type": "application/json",
        "Ocp-Apim-Subscription-Key": "c5e2d62a223946ca9e3f35b3fef75cef"
    }
    r = requests.post(url=url, data=payload, headers=headers)


def detect_face_api(face_url):
    url = "https://westeurope.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true"
    #payload = "{\"url\":\"" + face_url + "\"}"
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
        print(confidence)
        print(personId)
        name = get_name(personId)
        return name
    except:
        return "nullFaceId"
    print("a")


def get_name(personId):
    data = get_db()
    for d in data:

        d_personId = data[d]["personId"]
        if(d_personId == personId):
            name = data[d]["name"]
            return name
    return "nullName"


def check_name_exist(name):
    data = get_db()
    for d in data:
        if(data[d]["name"].lower() == name.lower()):
            print("DB"+data[d]["name"].lower())
            print("name"+name.lower())
            return True
    return False  # False


# train_face_api_personGroup_person()
#create_person("abc", "mustafa_home")
# print(identify_face_api("https://i.postimg.cc/Z585whdX/web.jpg"))
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

update_settigs_for_user("enez", settings_json)
data = get_db()
#print(data)

"""
data["person_1"]["home"] = "home1"

with open('db.json', "w") as f:
    f.write(str(data).replace("'", "\""))
    f.close()

with open('db.json') as f:
    data = json.load(f)

print(data)
"""
