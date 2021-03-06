import json
import db
import os
import random
import time

from flask import Flask, redirect, url_for, request, g
app = Flask(__name__)

upload_folder = "upload_folder"

UPLOAD_FOLDER = "upload_folder"
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])
counter = 0

messages = {"model_created": False}


@app.route('/register', methods=['POST', 'GET'])
def register():
    if request.method == 'POST':
        content = request.get_json()
        if "user_name" and "home" in content:
            user_name = content["user_name"]
            home = content["home"]
        else:
            response = {}
            response["Error"] = "input incorrect"
            return json.dumps(response, indent=4, sort_keys=False)
        response = db.create_person(user_name, home)
        is_created = {}
        is_created["is_created"] = str(response)
        if(response):
            return json.dumps(is_created, indent=4, sort_keys=False)

        return json.dumps(is_created, indent=4, sort_keys=False)
    else:
        data = db.get_db()
        return json.dumps(data, indent=4, sort_keys=False)

    return json.dumps(content, indent=4, sort_keys=False)


@app.route('/settings', methods=['POST', 'GET'])
def settings():
    if request.method == 'POST':
        content = request.get_json()
        user_name = content["user_name"]
        if "settings" in content:
            settings = content["settings"]
            data = db.update_settings_for_user(user_name,  settings)
            # return json.dumps(settings)
            return json.dumps(data, indent=4, sort_keys=False)
        else:
            data = db.get_settings_for_user(user_name)
            # db.create_person(user_name, home)
            return json.dumps(data, indent=4, sort_keys=False)

    else:
        data = db.get_db()
        return json.dumps(data, indent=4, sort_keys=False)

    return json.dumps(content, indent=4, sort_keys=False)


@app.route('/api/photo', methods=['POST', 'GET'])
def upload_file():
    start = time.time()

    if request.method == 'POST':
        print(request.get_json())
        files = request.files
        print("start")
        user_name = "test_person19"
        for f in files:
            file = request.files[f]
            counter = random.randint(1, 101)
            filename = "photo_"+str(counter)+".jpg"
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            if(True):
                filename = upload_folder + "/" + filename
                print(filename)
                identified_user, score = db.identify_face_api_with_binary(
                    filename)
                print(db.identify_face_api_with_binary(filename))

            if(len(os.listdir(upload_folder)) > 5 and False):
                file_list = os.listdir(upload_folder)
                for fi in file_list:
                    filename = upload_folder + "/" + fi
                    db.add_face_face_api_personGroup_person_binary(
                        user_name, filename)
                db.train_face_api_personGroup_person()
                #messages[model_created] = True
    response = {}
    response["username"] = identified_user
    response["score"] = score
    finish = time.time()
    print(finish-start)
    return json.dumps(response)


@app.route('/api/register_photo', methods=['POST', 'GET'])
def register_photo():
    start = time.time()
    user_name = "enezzzzzzzzz"
    if request.method == 'POST':
        print(request.get_json())
        files = request.files
        print("start")
        for f in files:
            file = request.files[f]
            counter = random.randint(1, 101)
            filename = "photo_"+str(counter)+".jpg"
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

            if(True):
                filename = upload_folder + "/" + filename
                print(filename)
                db.add_face_face_api_personGroup_person_binary(filename)
                identified_user, score = db.identify_face_api_with_binary(
                    filename)
                print(db.identify_face_api_with_binary(filename))

            if(len(os.listdir(upload_folder)) > 5 and False):
                file_list = os.listdir(upload_folder)
                for fi in file_list:
                    filename = upload_folder + "/" + fi
                    db.add_face_face_api_personGroup_person_binary(
                        user_name, filename)
                db.train_face_api_personGroup_person()
                #messages[model_created] = True
    response = {}
    response["username"] = identified_user
    response["score"] = score
    finish = time.time()
    print(finish-start)
    return json.dumps(response)


@app.route('/api/events', methods=['POST', 'GET'])
def user_events():
    if request.method == 'POST':
        response = {}
        content = request.get_json()
        user_name = content["user_name"]
        if "events" in content:
            events = content["events"]
            data = db.update_events_for_user(user_name, events)
            return json.dumps(data, indent=4, sort_keys=False)
        else:
            print("abc")
            data = db.get_user_events(user_name)
            print(data)
            return json.dumps(data, indent=4, sort_keys=False)


@app.route('/api/test', methods=['POST', 'GET'])
def user_test():
    if request.method == 'POST':
        content = request.get_json()
    return json.dumps(content, indent=4, sort_keys=False)


if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=5000)
