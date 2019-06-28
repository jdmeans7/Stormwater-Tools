import PyPDF2
from PIL import Image
import pytesseract
from pdf2image import convert_from_path

pdfFileObj = open('test.pdf', 'rb')
pdfReader = PyPDF2.PdfFileReader(pdfFileObj)
newPdfWriter = PyPDF2.PdfFileWriter()

for i in range(1, pdfReader.getNumPages(), 1):
    page = pdfReader.getPage(i)
    page.rotateClockwise(90)
    newPdfWriter.addPage(page)

newPdf = open('rotated.pdf', 'wb')
newPdfWriter.write(newPdf)
newPdf.close()

PDF = 'rotated.pdf'

pages = convert_from_path(PDF, 250, output_folder="output")

count = 1

for page in range(1, len(pages)):
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

