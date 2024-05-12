import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:forsythia/widgets/large_app_bar.dart';

class InventoryScreen extends StatefulWidget {
  const InventoryScreen({super.key});

  @override
  State<InventoryScreen> createState() => _InventoryScreenState();
}

class _InventoryScreenState extends State<InventoryScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(title: '보관함', sentence: '강아지 집을 꾸며보자!'),
    );
  }
}
