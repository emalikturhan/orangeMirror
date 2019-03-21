from googleapiclient.discovery import build
from httplib2 import Http
from oauth2client import file, client, tools
SCOPES = 'https://www.googleapis.com/auth/photoslibrary.readonly'

store = file.Storage('token-for-google.json')
creds = store.get()
if not creds or creds.invalid:
    flow = client.flow_from_clientsecrets('credentials.json', SCOPES)
    creds = tools.run_flow(flow, store)
gdriveservice = build('photoslibrary', 'v1', http=creds.authorize(Http()))

results = gdriveservice.albums().list(
    pageSize=10).execute()
items = results.get('albums', [])
for item in items:
    print(u'{0} ({1})'.format(item['title'].encode('utf8'), item['id']))
