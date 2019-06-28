from PIL import Image
import pytesseract
from pdf2image import convert_from_path

PDF = 'test.pdf'

pages = convert_from_path(PDF, 250, output_folder="output")

count = 1

for page in range(1, len(pages), 10):
    filename = "imgs/page_"+str(count)+".jpg"
    pages.pop().save(filename)
    count = count + 1

filelimit = count - 1

outfile = "out.txt"

f = open(outfile, "a")

for i in range(1, filelimit + 1):
    filename = "imgs/page_"+str(i)+".jpg"
    text = str((pytesseract.image_to_string(Image.open(filename))))
    text = text.replace('-\n', '')
    f.write(text)

f.close()

