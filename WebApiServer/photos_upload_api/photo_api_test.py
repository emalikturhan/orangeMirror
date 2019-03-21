
import os
import json
import numpy as np
from sklearn.ensemble import IsolationForest
import pickle
from flask_cors import CORS
from flask import Flask, redirect, url_for, request
from PIL import Image
import io
from werkzeug.utils import secure_filename

app = Flask(__name__)
CORS(app)

UPLOAD_FOLDER = "upload_folder"
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/api/photo', methods=['POST', 'GET'])
def upload_file():
    if request.method == 'POST':
        files = request.files
        for f in files:
            file = request.files[f]

            filename = "abc.jpg"
            print(filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
    response = {}
    response["username"] = "mustafa"
    return json.dumps(response)


@app.route('/api/test', methods=['POST', 'GET'])
def test_file():
    if request.method == 'POST':
        content = content = request.get_json()
        print(content)
    return "a"


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=3999)
