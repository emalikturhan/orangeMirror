import json
import db

from flask import Flask, redirect, url_for, request
app = Flask(__name__)


@app.route('/register', methods=['POST', 'GET'])
def register():
    if request.method == 'POST':
        content = request.get_json()
        print("JSON"+str(content))
        user_name = content["user_name"]
        home = content["home"]
        response = db.create_person(user_name, home)
        return response
    else:
        data = db.get_db()
        return json.dumps(data, indent=4, sort_keys=False)

    return json.dumps(content, indent=4, sort_keys=False)


@app.route('/settings', methods=['POST', 'GET'])
def settings():
    if request.method == 'POST':
        content = request.get_json()
        print("JSON"+str(content))
        user_name = content["user_name"]
        if "settings" in content:
            settings = content["settings"]
            data = db.update_settings_for_user(user_name, settings)
            #db.create_person(user_name, home)
            return json.dumps(data, indent=4, sort_keys=False)
        else:
            data = db.get_settings_for_user(user_name)
            #db.create_person(user_name, home)
            return json.dumps(data, indent=4, sort_keys=False)

    else:
        data = db.get_db()
        return json.dumps(data, indent=4, sort_keys=False)

    return json.dumps(content, indent=4, sort_keys=False)


if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0')
