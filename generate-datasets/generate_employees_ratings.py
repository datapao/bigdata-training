from random import shuffle, choice, gauss
import random
import time

items = 10000
positions = [
    "Software Developer", "Software Developer", "Software Developer",
    "Designer", "Business Analysts", "Manager", "UX Developer", "UX Developer",
    "Accountant"
]
start_date = "2011-12-02"
end_date = "2015-06-02"

names = []

with open("names.txt", "r") as f:
    for line in f:
        names.append(line.strip("\n"))

def generate_employees(path):
    shuffle(names)
    with open(path, 'w') as f:
        for name in names:
            f.write(name + "," + choice(positions) + "\n")

def strTimeProp(start, end, frmt, prop):
    stime = time.mktime(time.strptime(start, frmt))
    etime = time.mktime(time.strptime(end, frmt))
    ptime = stime + prop * (etime - stime)
    return time.strftime(frmt, time.localtime(ptime))


def randomDate(start, end, prop):
    return strTimeProp(start, end, '%Y-%m-%d', prop)

def generate_ratings(path):
    i = 0
    with open(path, 'w') as f:
        while (i < items):
            rating = int(gauss(0.743, 0.12) * 100)
            date = randomDate(start_date,end_date, random.random())
            f.write(date + "," + choice(names) + "," + str(rating) + "\n")
            i += 1


generate_employees("employees.csv")
generate_ratings("ratings.csv")
