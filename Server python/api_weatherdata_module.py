import os
from datetime import datetime
import database_module
class WeatherData:
    def __init__(self, coord, weather, base, main, visibility, wind, clouds, dt):
        self.coord = coord
        self.weather = weather
        self.base = base
        self.main = main
        self.visibility = visibility
        self.wind = wind
        self.clouds = clouds
        self.dt = dt


class Coord:
    def __init__(self, lon, lat):
        self.lon = lon
        self.lat = lat


class Weather:
    def __init__(self, id, main, description, icon):
        self.id = id
        self.main = main
        self.description = description
        self.icon = icon


class Main:
    def __init__(self, temp, feels_like, temp_min, temp_max, pressure, humidity):
        self.temp = temp
        self.feels_like = feels_like
        self.temp_min = temp_min
        self.temp_max = temp_max
        self.pressure = pressure
        self.humidity = humidity


class Wind:
    def __init__(self, speed, deg):
        self.speed = speed
        self.deg = deg


class Clouds:
    def __init__(self, all):
        self.all = all

import requests
base_url = "https://api.openweathermap.org/data/2.5/weather"
params ={
  "appid": os.getenv("appid")
  ,
  "lon": "106.8031"
  ,
  "units": "metric"
  ,
  "lat": "10.8698"
}
headers = {
    "accept":"application/json"
}
response = requests.get(base_url,params=params,headers= headers)
json_data = response.json()
coord = Coord(json_data['coord']['lon'], json_data['coord']['lat'])
weather = []
for weather_item in json_data['weather']:
    weather_obj = Weather(weather_item['id'], weather_item['main'], weather_item['description'], weather_item['icon'])
    weather.append(weather_obj)
base = json_data['base']
main = Main(json_data['main']['temp'], json_data['main']['feels_like'], json_data['main']['temp_min'],
            json_data['main']['temp_max'], json_data['main']['pressure'], json_data['main']['humidity'])
visibility = json_data['visibility']
wind = Wind(json_data['wind']['speed'], json_data['wind']['deg'])

clouds = Clouds(json_data['clouds']['all'])

dt = json_data['dt']

# Create WeatherData object
weather_data = WeatherData(coord, weather, base, main, visibility, wind, clouds, dt)
fullstatement = ("INSERT INTO weather_api "
                 "(date_primarykey,location_name,coord_lon, coord_lat, weather_id, weather_main, weather_description, "
                 "weather_icon, base, main_temp, main_feels_like, main_temp_min, main_temp_max, main_pressure, "
                 "main_humidity, visibility, wind_speed, wind_deg, clouds_all, dt)"
                 "VALUES (%s,%s,%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)")

current_time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
data = (
    current_time,
    "UIT",
    weather_data.coord.lon,
    weather_data.coord.lat,
    weather_data.weather[0].id,
    weather_data.weather[0].main,
    weather_data.weather[0].description,
    weather_data.weather[0].icon,
    weather_data.base,
    weather_data.main.temp,
    weather_data.main.feels_like,
    weather_data.main.temp_min,
    weather_data.main.temp_max,
    weather_data.main.pressure,
    weather_data.main.humidity,
    weather_data.visibility,
    weather_data.wind.speed,
    weather_data.wind.deg,
    weather_data.clouds.all,
    weather_data.dt
)
