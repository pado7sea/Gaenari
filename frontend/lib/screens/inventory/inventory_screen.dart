import 'package:flutter/material.dart';
import 'package:forsythia/models/inventory/item_info.dart';
import 'package:forsythia/models/inventory/set_list.dart';
import 'package:forsythia/service/inventory_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/large_app_bar.dart';
import 'inventory_info.dart';

class InventoryScreen extends StatefulWidget {
  const InventoryScreen({super.key});

  @override
  State<InventoryScreen> createState() => _InventoryScreenState();
}

class _InventoryScreenState extends State<InventoryScreen> {
  List<Set> set = [];
  @override
  void initState() {
    super.initState();
    _getSet();
  }

  _getSet() async {
    SetList setList = await InventoryService.fetchSetList();
    setState(() {
      set = setList.data!;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(title: '보관함', sentence: '강아지 집을 꾸며보자!'),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              _buildRowOfItems(0),
              SizedBox(height: 16), // 행 사이 간격 조절
              _buildRowOfItems(2),
              SizedBox(height: 16), // 행 사이 간격 조절
              _buildRowOfItems(4),
              SizedBox(height: 16), // 행 사이 간격 조절
              _buildRowOfItems(6),
              SizedBox(height: 16), // 행 사이 간격 조절
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildRowOfItems(int startIndex) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly, // 아이템을 가운데 정렬
      children: [
        Flexible(flex: 1, child: _buildItemTile(info[startIndex], startIndex)),
        SizedBox(width: 16),
        Flexible(
            flex: 1,
            child: _buildItemTile(info[startIndex + 1], startIndex + 1)),
      ],
    );
  }

  Widget _buildItemTile(ItemInfo itemInfo, int index) {
    return InkWell(
      onTap: () {
        // 각 아이템을 눌렀을 때의 동작 추가 가능
      },
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            decoration: myBorderBoxDecoration,
            height: 160,
          ),
          SizedBox(height: 8.0), // 아이템 이름과 세트 이름 사이 간격 조정
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text16(
                text: "  ${itemInfo.setName!}",
                bold: true,
              ),
              Text16(
                text: "${set[index].itemCnt.toString()}/6  ",
                textColor: myGrey,
              )
            ],
          ),
        ],
      ),
    );
  }
}
