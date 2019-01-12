# 모듈화 시키기
import requests
from bs4 import BeautifulSoup

def crawl(url):
    result = requests.get(url)
    bs_obj = BeautifulSoup(result.content, "html.parser")
    return bs_obj







