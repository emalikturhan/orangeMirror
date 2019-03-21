import requests
import json
import httplib2
from oauth2client.client import flow_from_clientsecrets, OAuth2Credentials

api_key = "AIzaSyDc3AOLR8iT_o-QGpnxfo5peSkWBNYaqfU"


with open('credentials.json') as f:
    data = json.load(f)
abcde = json.dumps(data["installed"])
print(abcde)

credentials = OAuth2Credentials.from_json(abcde)
#h = httplib2.Http()
#h = credentials.authorize(h)


"""
auth_uri = flow.step1_get_authorize_url()
code = flow.step1_get_device_and_user_codes()
print(code)
credentials = flow.step2_exchange("ZJB-ZWK-NNZ")
print(credentials)

""""""

url = "https://photoslibrary.googleapis.com/v1/mediaItems?key=api_key"
r = requests.get(url=url, headers=headers)
print(r.content)

""""""
print()
r = requests.get(url=auth_uri).json()
print(r)
print()
print()


def upload(service, file):
    f = open(file, 'rb').read()

    url = 'https://photoslibrary.googleapis.com/v1/uploads'
    headers = {
        'Authorization': "Bearer " + service._http.request.credentials.access_token,
        'Content-Type': 'application/octet-stream',
        'X-Goog-Upload-File-Name': file,
        'X-Goog-Upload-Protocol': "raw",
    }

    r = requests.post(url, data=f, headers=headers)
    print('\nUpload token: %s' % r.content)
    return r.content


def createItem(service, upload_token, albumId):
    url = 'https://photoslibrary.googleapis.com/v1/mediaItems:batchCreate'

    body = {
        'newMediaItems': [
            {
                "description": "test upload",
                "simpleMediaItem": {
                    "uploadToken": upload_token
                }
            }
        ]
    }

    if albumId is not None:
        body['albumId'] = albumId

    bodySerialized = json.dumps(body)
    headers = {
        'Authorization': "Bearer " + service._http.request.credentials.access_token,
        'Content-Type': 'application/json',
    }

    r = requests.post(url, data=bodySerialized, headers=headers)
    print('\nCreate item response: %s' % r.content)
    return r.content



upload_token = upload(service, './path_to_image.png')
response = createItem(service, upload_token, album['id'])

headers = {
    "content-type": "application/json",
    "Authorization": ""
}
with open('credentials.json') as f:
    data = json.load(f)

print(data)
headers["Authorization"] = "Bearer " + data["installed"]["client_secret"]

r = requests.get(url=url, headers=headers)
print(r.content)
"""
