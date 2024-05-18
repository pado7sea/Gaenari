// ignore_for_file: use_build_context_synchronously

import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/inventory/change_item.dart';
import 'package:forsythia/models/inventory/item_list.dart';
import 'package:forsythia/models/inventory/item_purchase.dart';
import 'package:forsythia/models/inventory/mate_home.dart';
import 'package:forsythia/models/inventory/my_item.dart';
import 'package:forsythia/models/inventory/pet_list.dart';
import 'package:forsythia/models/inventory/set_list.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

// InventoryService 클래스
// 인벤토리 관련 서비스, 친구집 방문도 여기
class InventoryService {
  static const String baseUrl = 'https://api.gaenari.kr/api/inventory-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 친구집 방문 - 멤버아이디를 파라미터로
  static Future<MateHome> fetchMateHome(BuildContext context, memberId) async {
    try {
      return fetchGetData(context, 'inventory/mate/$memberId')
          .then((data) => MateHome.fromJson(data));
    } catch (e) {
      print('Error fetching Mate Home: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 아이템구매 - 파라미터없음
  static Future<ItemPurchase> fetchItemPurchase(BuildContext context) async {
    try {
      return fetchGetData(context, 'inventory/purchase')
          .then((data) => ItemPurchase.fromJson(data));
    } catch (e) {
      print('Error fetching Item Purchase: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 펫 리스트조회 - 파라미터없음
  static Future<PetList> fetchPetList(BuildContext context) async {
    try {
      return fetchGetData(context, 'inventory/pets')
          .then((data) => PetList.fromJson(data));
    } catch (e) {
      print('Error fetching Pet List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 아이템 리스트조회 (세트별로 몇개씩 가지고있는지)- 파라미터없음
  static Future<SetList> fetchSetList(BuildContext context) async {
    try {
      return fetchGetData(context, 'inventory/items')
          .then((data) => SetList.fromJson(data));
    } catch (e) {
      print('Error fetching Set List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 아이템 리스트조회 -세트아이디를 파라미터로
  static Future<ItemList> fetchItemList(BuildContext context, setId) async {
    try {
      return fetchGetData(context, 'inventory/items/$setId')
          .then((data) => ItemList.fromJson(data));
    } catch (e) {
      print('Error fetching Item List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 내가 착용한 아이템 조회
  static Future<MyItem> fetchMyItem(BuildContext context) async {
    try {
      return fetchGetData(context, 'inventory/equip')
          .then((data) => MyItem.fromJson(data));
    } catch (e) {
      print('Error fetching My Item: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 착용한 아이템 바꾸기 - 카테고리는 스트링, 아이템아이디는 인트 Wall, Floor, Rug, Bowl, Cushion, Toy 중에 하나
  static Future<ChangeItem> fetchChangeItem(
      BuildContext context, category, itemId) async {
    try {
      return fetchPutData(context, 'inventory/equip/$category/$itemId')
          .then((data) => ChangeItem.fromJson(data));
    } catch (e) {
      print('Error fetching Change Item: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // get요청
  static Future<dynamic> fetchGetData(
      BuildContext context, String endpoint) async {
    try {
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
    } catch (e) {
      print('Error fetching Get Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // put요청
  static Future<dynamic> fetchPutData(
      BuildContext context, String endpoint) async {
    try {
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
    } catch (e) {
      print('Error fetching Put Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // post요청
  static Future<dynamic> fetchPostData(
      BuildContext context, String endpoint) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.post(
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
    } catch (e) {
      print('Error fetching Post Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }
}
