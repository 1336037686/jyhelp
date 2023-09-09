<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people" @click="$router.push('/customer/member')">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            注册会员
          </div>
          <div class="card-panel-num">
            {{ people.value }}
            <el-tooltip v-if="people.subValue !== null" :content="people.subValueRemark" placement="bottom" effect="light">
              <span v-if="people.subValueState > 0" style="margin-left: 5px;color: #2ac06d;font-size: 13px"><i class="el-icon-caret-top" /> {{ people.subValue }}</span>
              <span v-if="people.subValueState === 0" style="margin-left: 5px;color: #4A9FF9;font-size: 13px"><i class="el-icon-d-caret" /> {{ people.subValue }}</span>
              <span v-if="people.subValueState < 0" style="margin-left: 5px;color: red;font-size: 13px"><i class="el-icon-caret-bottom" /> {{ people.subValue }}</span>
            </el-tooltip>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people" @click="$router.push('/transaction/order')">
          <svg-icon icon-class="order" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            今日订单
          </div>
          <div class="card-panel-num">
            {{ orderToday.value }}
            <el-tooltip v-if="orderToday.subValue !== null" :content="orderToday.subValueRemark" placement="bottom" effect="light">
              <span v-if="orderToday.subValueState > 0" style="margin-left: 5px;color: #2ac06d;font-size: 13px"><i class="el-icon-caret-top" /> {{ orderToday.subValue }}</span>
              <span v-if="orderToday.subValueState === 0" style="margin-left: 5px;color: #4A9FF9;font-size: 13px"><i class="el-icon-d-caret" /> {{ orderToday.subValue }}</span>
              <span v-if="orderToday.subValueState < 0" style="margin-left: 5px;color: red;font-size: 13px"><i class="el-icon-caret-bottom" /> {{ orderToday.subValue }}</span>
            </el-tooltip>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people" @click="$router.push('/transaction/order')">
          <svg-icon icon-class="order2" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            本月订单
          </div>
          <div class="card-panel-num">
            {{ orderMonth.value }}
            <el-tooltip v-if="orderMonth.subValue !== null" :content="orderMonth.subValueRemark" placement="bottom" effect="light">
              <span v-if="orderMonth.subValueState > 0" style="margin-left: 5px;color: #2ac06d;font-size: 13px"><i class="el-icon-caret-top" /> {{ orderMonth.subValue }}</span>
              <span v-if="orderMonth.subValueState === 0" style="margin-left: 5px;color: #4A9FF9;font-size: 13px"><i class="el-icon-d-caret" /> {{ orderMonth.subValue }}</span>
              <span v-if="orderMonth.subValueState < 0" style="margin-left: 5px;color: red;font-size: 13px"><i class="el-icon-caret-bottom" /> {{ orderMonth.subValue }}</span>
            </el-tooltip>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people" @click="$router.push('/transaction/order')">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            总计营收
          </div>
          <div class="card-panel-num">
            {{ totalMoney.value }}
            <el-tooltip v-if="totalMoney.subValue !== null" :content="totalMoney.subValueRemark" placement="bottom" effect="light">
              <span v-if="totalMoney.subValueState > 0" style="margin-left: 5px;color: #2ac06d;font-size: 13px"><i class="el-icon-caret-top" /> {{ totalMoney.subValue }}</span>
              <span v-if="totalMoney.subValueState === 0" style="margin-left: 5px;color: #4A9FF9;font-size: 13px"><i class="el-icon-d-caret" /> {{ totalMoney.subValue }}</span>
              <span v-if="totalMoney.subValueState < 0" style="margin-left: 5px;color: red;font-size: 13px"><i class="el-icon-caret-bottom" /> {{ totalMoney.subValue }}</span>
            </el-tooltip>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people" @click="$router.push('/shop/product')">
          <svg-icon icon-class="shopping" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            上架商品
          </div>
          <div class="card-panel-num">
            {{ products.value }}
            <el-tooltip v-if="products.subValue !== null" :content="products.subValueRemark" placement="bottom" effect="light">
              <span v-if="products.subValueState > 0" style="margin-left: 5px;color: #2ac06d;font-size: 13px"><i class="el-icon-caret-top" /> {{ products.subValue }}</span>
              <span v-if="products.subValueState === 0" style="margin-left: 5px;color: #4A9FF9;font-size: 13px"><i class="el-icon-d-caret" /> {{ products.subValue }}</span>
              <span v-if="products.subValueState < 0" style="margin-left: 5px;color: red;font-size: 13px"><i class="el-icon-caret-bottom" /> {{ products.subValue }}</span>
            </el-tooltip>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people" @click="$router.push('/service/handle-service')">
          <svg-icon icon-class="service" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            累计服务
          </div>
          <div class="card-panel-num">
            {{ services.value }}
            <el-tooltip v-if="services.subValue !== null" :content="services.subValueRemark" placement="bottom" effect="light">
              <span v-if="services.subValueState > 0" style="margin-left: 5px;color: #2ac06d;font-size: 13px"><i class="el-icon-caret-top" /> {{ services.subValue }}</span>
              <span v-if="services.subValueState === 0" style="margin-left: 5px;color: #4A9FF9;font-size: 13px"><i class="el-icon-d-caret" /> {{ services.subValue }}</span>
              <span v-if="services.subValueState < 0" style="margin-left: 5px;color: red;font-size: 13px"><i class="el-icon-caret-bottom" /> {{ services.subValue }}</span>
            </el-tooltip>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import dashboardApi from '@/api/module/dashboard/dashboard-api'
export default {
  data() {
    return {
      people: {
        name: null,
        code: null,
        value: null,
        subValue: null,
        subValueState: 0,
        subValueRemark: null
      },
      orderToday: {
        name: null,
        code: null,
        value: null,
        subValue: null,
        subValueState: 0,
        subValueRemark: null
      },
      orderMonth: {
        name: null,
        code: null,
        value: null,
        subValue: null,
        subValueState: 0,
        subValueRemark: null
      },
      totalMoney: {
        name: null,
        code: null,
        value: null,
        subValue: null,
        subValueState: 0,
        subValueRemark: null
      },
      products: {
        name: null,
        code: null,
        value: null,
        subValue: null,
        subValueState: 0,
        subValueRemark: null
      },
      services: {
        name: null,
        code: null,
        value: null,
        subValue: null,
        subValueState: 0,
        subValueRemark: null
      }
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      dashboardApi.getPanelGroup().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          const data = res.data[i]
          if (data.code === 'people') this.people = res.data[i]
          if (data.code === 'orderToday') this.orderToday = res.data[i]
          if (data.code === 'orderMonth') this.orderMonth = res.data[i]
          if (data.code === 'totalMoney') this.totalMoney = res.data[i]
          if (data.code === 'products') this.products = res.data[i]
          if (data.code === 'services') this.services = res.data[i]
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 10px;

  .card-panel-col {
    margin-bottom: 20px;
    padding-left: 5px!important;
    padding-right: 5px!important;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        text-align: right;
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
        text-align: right;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
