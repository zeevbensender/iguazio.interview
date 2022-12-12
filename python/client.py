import requests
from time import perf_counter

print("<<< Starting >>>")

def sync_version(urls):
    for url in urls:
        # r = requests.get('https://jsonplaceholder.typicode.com/posts/')
        # r = requests.get('https://catfact.ninja/fact')
        r = requests.get(f'http://127.0.0.1:8081/{url}')
        print(r.json())

        print("\n")

start = perf_counter()

sync_version(range(1, 2500))

stop = perf_counter()
print("time taken:", stop - start)