from concurrent.futures import ThreadPoolExecutor

from time import perf_counter

start = perf_counter()

import requests

urls = range(1, 25000)


def get_data(url):
    r = requests.get(f'http://127.0.0.1:8081/{url}')
    print(r.json())


with ThreadPoolExecutor() as executor:
    executor.map(get_data, urls)

stop = perf_counter()
print("time taken:", stop - start)