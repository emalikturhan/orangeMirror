events = ["abc,123", "werw,234", "dsdasda,13231", "eweqw,13123"]

name = "mustafa"

main_json = {}  # json object init
main_json["user_name"] = name  # obj.put("username",name)
sub_json_array = []
for e in events:
    sub_json = {}  # json object init

    event_name = e.split(",")[0]
    event_date = e.split(",")[1]

    sub_json[event_name] = event_date  # sub_obj.put(event_name,event_date)

    sub_json_array.append(sub_json)
print("sub_json_array")
print(sub_json_array)
print("main_json")
main_json["events"] = sub_json_array
print(main_json)
