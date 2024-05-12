import 'dart:convert';
import 'package:forsythia/models/inventory/change_item.dart';
import 'package:forsythia/models/inventory/item_list.dart';
import 'package:forsythia/models/inventory/item_purchase.dart';
import 'package:forsythia/models/inventory/mate_home.dart';
import 'package:forsythia/models/inventory/my_item.dart';
import 'package:forsythia/models/inventory/pet_list.dart';
import 'package:forsythia/models/inventory/set_list.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// InventoryService 클래스
// 인벤토리 관련 서비스, 친구집 방문도 여기
class InventoryService {
  static const String baseUrl = 'https://api.gaenari.kr/api/inventory-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 친구집 방문 - 멤버아이디를 파라미터로
  static Future<MateHome> fetchMateHome(memberId) async {
    return fetchGetData('inventory/mate/$memberId')
        .then((data) => MateHome.fromJson(data));
  }

  // 아이템구매 - 파라미터없음
  static Future<ItemPurchase> fetchItemPurchase() async {
    return fetchGetData('inventory/purchase')
        .then((data) => ItemPurchase.fromJson(data));
  }

  // 펫 리스트조회 - 파라미터없음
  static Future<PetList> fetchPetList() async {
    return fetchGetData('inventory/pets')
        .then((data) => PetList.fromJson(data));
  }

  // 아이템 리스트조회 (세트별로 몇개씩 가지고있는지)- 파라미터없음
  static Future<SetList> fetchSetList() async {
    return fetchGetData('inventory/items')
        .then((data) => SetList.fromJson(data));
  }

  // 아이템 리스트조회 -세트아이디를 파라미터로
  static Future<ItemList> fetchItemList(setId) async {
    return fetchGetData('inventory/items/$setId')
        .then((data) => ItemList.fromJson(data));
  }

  // 내가 착용한 아이템 조회
  static Future<MyItem> fetchMyItem() async {
    return fetchGetData('inventory/equip')
        .then((data) => MyItem.fromJson(data));
  }

  // 착용한 아이템 바꾸기 - 카테고리는 스트링, 아이템아이디는 인트 Wall, Floor, Rug, Bowl, Cushion, Toy 중에 하나
  static Future<ChangeItem> fetchChangeItem(category, itemId) async {
    return fetchPutData('inventory/equip/$category/$itemId')
        .then((data) => ChangeItem.fromJson(data));
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

  // post요청
  static Future<dynamic> fetchPostData(String endpoint) async {
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
  }
}
