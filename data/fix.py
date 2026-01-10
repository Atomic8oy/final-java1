file = open("enrollments.data", 'r')
mess = file.read()
file.close()

out = ""

line = ""
for c in mess:
    if (line == "null"):
        out += line + "\n"
        line = ""
    
    if (c in ['n', 'u', 'l', 'l']):
        line += c
    else:
        out += c + "\n"

file = open("fixed.data", 'w')
file.write(out)
file.close()

input()