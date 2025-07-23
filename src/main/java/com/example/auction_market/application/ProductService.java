package com.example.auction_market.application;

import com.example.auction_market.domain.auction.Auction;
import com.example.auction_market.domain.auction.AuctionRepository;
import com.example.auction_market.domain.bid.BidRepository;
import com.example.auction_market.domain.product.Product;
import com.example.auction_market.domain.product.ProductRepository;
import com.example.auction_market.dto.productDto.ProductUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;

    public void productUpload(ProductUploadRequest request) {

        // 이미지 처리, 판매자 정보 필요 (세션으로 받아와야하나?)
        Product product = Product.builder()
                .category(Product.Category.valueOf(request.getProductCategory().toUpperCase()))
                .title(request.getProductTitle())
                .bidCount(0)
                .description(request.getProductDescription())
                .highestPrice(request.getHighestPrice())
                .buyNowPrice(request.getBuyNowPrice())
                .build();


        Auction auction = Auction.builder()
                .product(product)
                .startPrice(request.getHighestPrice())
                .endTime(request.getEndDate())
                .status(Auction.Status.READY)
                .build();

        productRepository.save(product);
    }

}
