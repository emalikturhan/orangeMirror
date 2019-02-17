import json
import db

from flask import Flask, redirect, url_for, request
app = Flask(__name__)


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
            """print("Dumps")
            print(json.dumps(settings))
            print("str")
            print(str(settings))
            print("LOADS")
            print(json.loads(json.dumps(settings)))"""
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


if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0')
