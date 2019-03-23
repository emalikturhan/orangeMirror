

import json
import db
import os
import random
import time

from flask import Flask, redirect, url_for, request, g
app = Flask(__name__)

upload_folder = "upload_folder"


@app.route('/api/register_face', methods=['POST', 'GET'])
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


if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=5000)
