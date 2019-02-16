FROM python:3.7.2-stretch
COPY . /app
WORKDIR /app
RUN pip3 install -r requirements.txt
ENTRYPOINT ["python3"]
CMD ["web_api_service.py"]