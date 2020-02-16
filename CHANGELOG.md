# CHANGELOG

## [[2.8.1]](https://github.com/Clarifai/clarifai-java/releases/tag/2.8.1) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.8.1) - 2020-02-16

### Added
- Add the appearances fields to Detection

### Changed
- Rename Detection's boundingBox field to crop, to make it consistent with other classes


## [[2.8.0]](https://github.com/Clarifai/clarifai-java/releases/tag/2.8.0) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.8.0) - 2020-02-14

See the API changes for more details on the Face proto deprecation and the facedetect* model type cleanup:
https://docs.clarifai.com/product-updates/upcoming-api-changes

### Changed
- Use Protobuf/gRPC for JSON (de)serialization

### Added
- DetectionModel

### Removed
- DemographicsModel, LogoModel, FaceConceptsModel, FaceDetectionModel, FocusModel.
- Feedback

## [[2.7.0]](https://github.com/Clarifai/clarifai-java/releases/tag/2.7.0) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.7.0) - 2018-12-10

### Added
- Sample milliseconds parameter for video prediction

## [[2.6.1]](https://github.com/Clarifai/clarifai-java/releases/tag/2.6.1) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.6.1) - 2018-12-03

### Fixed
- Added deserialization of file images in the workflow predict response

## [[2.6.0]](https://github.com/Clarifai/clarifai-java/releases/tag/2.6.0) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.6.0) - 2018-10-19

### Added
- Moderation solution

## [[2.5.2]](https://github.com/Clarifai/clarifai-java/releases/tag/2.5.2) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.5.2) - 2018-10-05

### Added
- In a predict request, allow specifying the model version ID directly

## [[2.5.1]](https://github.com/Clarifai/clarifai-java/releases/tag/2.5.1) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.5.1) - 2018-05-07

### Fixed
- Output info serialization fixed when creating models

## [[2.5.0]](https://github.com/Clarifai/clarifai-java/releases/tag/2.5.0) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.5.0) - 2018-04-09

### Added
- Support for custom face recognition

## [[2.4.0]](https://github.com/Clarifai/clarifai-java/releases/tag/2.4.0) - [jCenter](https://bintray.com/clarifai/Clarifai/Clarifai/2.4.0) - 2017-11-14

### Added
- Add new default models: celebrity, demographics, face-embed
