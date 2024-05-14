import 'dart:convert';
import 'package:forsythia/models/pet/pet_adopt.dart';
import 'package:forsythia/models/pet/pet_res.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// 펫 서비스
class PetService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 강아지입양
  static Future<PetRes> fetchPetAdopt(pet) async {
    return fetchPostBodyData('pet/adopt', pet)
        .then((data) => PetRes.fromJson(data));
  }

  // 강아지변경
  static Future<PetRes> fetchPetPartner(id) async {
    return fetchPutData('pet/partner/$id')
        .then((data) => PetRes.fromJson(data));
  }

  // 바디있는 포스트요청
  static Future<dynamic> fetchPostBodyData(
      String endpoint, PetAdopt pet) async {
    String? token = await secureStorageService.getToken();
    final response = await http.post(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token',
      },
      body: json.encode(pet.toJson()),
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return data;
      } else {
        throw Exception("status : ${data['status']}");
      }
    } else {
      throw Exception('statusCode : ${response.statusCode}');
    }
  }

  // get요청
  static Future<dynamic> fetchGetData(String endpoint) async {
    String? token = await secureStorageService.getToken();
    final response = await http.get(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token'
      },
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return data;
      } else {
        throw Exception('Failed to load data');
      }
    } else {
      throw Exception('Failed to load data');
    }
  }

  // put요청
  static Future<dynamic> fetchPutData(String endpoint) async {
    String? token = await secureStorageService.getToken();
    final response = await http.put(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token'
      },
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return data;
      } else {
        throw Exception('Failed to load data');
      }
    } else {
      throw Exception('Failed to load data');
    }
  }
}
