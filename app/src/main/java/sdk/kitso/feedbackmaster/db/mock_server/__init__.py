import os

from flask import Flask, jsonify
from faker import Faker

Faker.seed(1000)
fake = Faker()

def create_app(test_config=None):
    app = Flask(__name__, instance_relative_config=True)
    app.config.from_mapping(
        SECRET_KEY="dev",
        DATABASE=os.path.join(app.instance_path, 'mock_database.sqlite')
    )

    if test_config is None:
        app.config.from_pyfile('config.py', silent=True)
    else:
        app.config.from_mapping(test_config)

    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    surveys_list = []
    for i in range(1000):
        company = fake.company()
        survey = fake.catch_phrase()
        surveys_list.append(dict(company_name=company, survey_name=survey, survey_id=i))

    @app.route("/surveys/<int:id>", methods=["GET", "POST"])
    def surveys(id):
        print(type(surveys_list))
        return jsonify(surveys_list)

    return app